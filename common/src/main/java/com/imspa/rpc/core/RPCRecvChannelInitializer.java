package com.imspa.rpc.core;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.Map;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-07 18:38
 */
public class RPCRecvChannelInitializer extends ChannelInitializer<SocketChannel> {
    final public static int MESSAGE_LENGTH = 4;
    private Map<String, Object> handlerMap;

    RPCRecvChannelInitializer(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, RPCRecvChannelInitializer.MESSAGE_LENGTH, 0, RPCRecvChannelInitializer.MESSAGE_LENGTH));
        pipeline.addLast(new LengthFieldPrepender(RPCRecvChannelInitializer.MESSAGE_LENGTH));
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
        pipeline.addLast(new RPCRecvHandler(handlerMap));
    }
}
