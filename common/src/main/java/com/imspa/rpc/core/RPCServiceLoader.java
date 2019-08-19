package com.imspa.rpc.core;

import com.imspa.rpc.threadpool.RPCThreadPool;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pann
 * @Description Singleton
 * @Date 2019-08-07 17:50
 */
public class RPCServiceLoader {
    private volatile static RPCServiceLoader rpcServiceLoader;
    private final static String DELIMITER = ":";

    private final static int parallel = Runtime.getRuntime().availableProcessors() * 2;
    private EventLoopGroup eventLoopGroup = new NioEventLoopGroup(parallel);
    private static ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) RPCThreadPool.getExecutor(16, -1); //TODO thread core
    private RPCSendHandler messageSendHandler = null;

    private Lock lock = new ReentrantLock();
    private Condition signal = lock.newCondition();

    private RPCServiceLoader() {
    }

    public static RPCServiceLoader getInstance() {
        if (rpcServiceLoader == null) {
            synchronized (RPCServiceLoader.class) {
                if (rpcServiceLoader == null) {
                    rpcServiceLoader = new RPCServiceLoader();
                }
            }
        }
        return rpcServiceLoader;
    }

    public void load(String serverAddress) { //every interface have its own accept thread
        String[] ipAddr = serverAddress.split(RPCServiceLoader.DELIMITER);
        if (ipAddr.length == 2) {
            String host = ipAddr[0];
            int port = Integer.parseInt(ipAddr[1]);
            final InetSocketAddress remoteAddr = new InetSocketAddress(host, port);

            threadPoolExecutor.submit(new RPCSendInitializeTask(eventLoopGroup, remoteAddr));
        }
    }

    public void setMessageSendHandler(RPCSendHandler messageInHandler) {
        try {
            lock.lock();
            this.messageSendHandler = messageInHandler;
            signal.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public RPCSendHandler getMessageSendHandler() throws InterruptedException {
        try {
            lock.lock();
            if (messageSendHandler == null) {
                signal.await();
            }
            return messageSendHandler;
        } finally {
            lock.unlock();
        }
    }

    public void unLoad() {
        messageSendHandler.close();
        threadPoolExecutor.shutdown();
        eventLoopGroup.shutdownGracefully();
    }
}
