package com.imspa.rpc.core;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-07 18:01
 */
public class RPCSendChannelInitializer extends ChannelInitializer<SocketChannel> {
    final public static int MESSAGE_LENGTH = 4;


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, RPCSendChannelInitializer.MESSAGE_LENGTH, 0, RPCSendChannelInitializer.MESSAGE_LENGTH));
        pipeline.addLast(new LengthFieldPrepender(RPCSendChannelInitializer.MESSAGE_LENGTH));
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
        pipeline.addLast("ping", new IdleStateHandler(45, 30, 20, TimeUnit.SECONDS));
        pipeline.addLast(new RPCSendHandler());
    }
}
