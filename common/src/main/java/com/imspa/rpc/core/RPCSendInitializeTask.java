package com.imspa.rpc.core;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-07 17:58
 */
public class RPCSendInitializeTask implements Runnable {
    private static final Logger logger = LogManager.getLogger(RPCSendInitializeTask.class);
    private EventLoopGroup eventLoopGroup;
    private InetSocketAddress serverAddress;

    RPCSendInitializeTask(EventLoopGroup eventLoopGroup, InetSocketAddress serverAddress) {
        this.eventLoopGroup = eventLoopGroup;
        this.serverAddress = serverAddress;
    }

    @Override
    public void run() {
        Bootstrap b = new Bootstrap();
        b.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .remoteAddress(serverAddress);
        b.handler(new RPCSendChannelInitializer());

        ChannelFuture channelFuture = b.connect();
        channelFuture.addListener((ChannelFutureListener) futureListener -> {
            if (futureListener.isSuccess()) {
                RPCSendHandler handler = futureListener.channel().pipeline().get(RPCSendHandler.class);
                RPCServiceLoader.getInstance().setMessageSendHandler(handler);
            } else {
                eventLoopGroup.schedule(() -> {
                    logger.error("RPC Service is down,start to reconnecting to: {}:{}",serverAddress.getAddress().getHostAddress(),serverAddress.getPort());
                    try {
                        run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 30, TimeUnit.SECONDS);
            }
        });
    }
}
