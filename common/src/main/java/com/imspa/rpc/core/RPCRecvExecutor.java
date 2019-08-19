package com.imspa.rpc.core;

import com.imspa.rpc.model.RPCInterfacesWrapper;
import com.imspa.rpc.threadpool.NamedThreadFactory;
import com.imspa.rpc.threadpool.RPCThreadPool;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.nio.channels.spi.SelectorProvider;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Pann
 * @description
 * @date 2019-08-07 18:16
 */
public class RPCRecvExecutor implements ApplicationContextAware, InitializingBean {
    private static final Logger logger = LogManager.getLogger(RPCRecvExecutor.class);

    private String addr;

    private final static String DELIMITER = ":";
    private Map<String, Object> handlerMap = new ConcurrentHashMap<>();

    private static volatile ThreadPoolExecutor threadPoolExecutor;

    public RPCRecvExecutor(String serverAddr) {
        this.addr = serverAddr;
    }

    public static void submit(Runnable task) {
        if (threadPoolExecutor == null) {
            synchronized (RPCRecvExecutor.class) {
                if (threadPoolExecutor == null) {
                    threadPoolExecutor = (ThreadPoolExecutor) RPCThreadPool.getExecutor(16, 1); //TODO thread core
                }
            }
        }

        threadPoolExecutor.submit(task);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RPCInterfacesWrapper containerWrapper =
                (RPCInterfacesWrapper) applicationContext.getBean("serviceContainer");
        List<String> container = containerWrapper.getInterfaces();

        container.forEach(key -> {
            try {
                handlerMap.put(key, applicationContext.getBean(Class.forName(key)));
            } catch (ClassNotFoundException e) {
                logger.error("The Interface config error, In \"{}\"", key);
            }
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        new Thread(()->{
            ThreadFactory threadRpcFactory = new NamedThreadFactory(Boolean.TRUE);

            int parallel = Runtime.getRuntime().availableProcessors() * 2;

            EventLoopGroup boss = new NioEventLoopGroup();
            EventLoopGroup worker = new NioEventLoopGroup(parallel, threadRpcFactory, SelectorProvider.provider());

            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                        .childHandler(new RPCRecvChannelInitializer(handlerMap))
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);

                String[] serviceAddr = addr.split(RPCRecvExecutor.DELIMITER);

                if (serviceAddr.length == 2) {
                    String host = serviceAddr[0];
                    int port = Integer.parseInt(serviceAddr[1]);
                    ChannelFuture future = bootstrap.bind(host, port).sync();
                    logger.info("Netty RPC Service start success ip:{} port:{}", host, port);
                    future.channel().closeFuture().sync();
                } else {
                    logger.error("Netty RPC Service start fail!! Because of the service addr:{}", addr);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                worker.shutdownGracefully();
                boss.shutdownGracefully();
            }
        },"RPCServer-MainThread").start();

    }
}
