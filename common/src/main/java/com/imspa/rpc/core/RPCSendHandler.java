package com.imspa.rpc.core;

import com.imspa.rpc.model.RPCRequest;
import com.imspa.rpc.model.RPCResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-07 18:04
 */
public class RPCSendHandler extends ChannelInboundHandlerAdapter {
    private final static Logger logger = LogManager.getLogger(RPCSendHandler.class);
    private ConcurrentHashMap<String, RPCCallBack> callBackMap = new ConcurrentHashMap<>();
    private static final String HEART_BEAT = "1_";

    private volatile Channel channel;
    private SocketAddress remoteAddr;

    public Channel getChannel() {
        return channel;
    }

    public SocketAddress getRemoteAddr() {
        return remoteAddr;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.remoteAddr = this.channel.remoteAddress();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        new RPCSendInitializeTask(channel.eventLoop(), (InetSocketAddress) remoteAddr).run();
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);

        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                logger.debug("RPC Service is down");
            } else if (event.state().equals(IdleState.WRITER_IDLE)) {
                logger.debug("client send heat beat package");
                channel.writeAndFlush(HEART_BEAT);
            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                logger.debug("no message send/get with RPC service some sec");
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg.getClass().equals(String.class)) {
            logger.debug("RPC Client get response(HEART_BEAT):{}",msg);
        } else {
            RPCResponse response = (RPCResponse) msg;
            String invokeId = response.getInvokeId();
            RPCCallBack callBack = callBackMap.get(invokeId);
            if (callBack != null) {
                callBackMap.remove(invokeId);
                callBack.over(response);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("netty connect error", cause);
        ctx.close();
    }

    public void close() {
        channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    public RPCCallBack sendRequest(RPCRequest request) {
        RPCCallBack callBack = new RPCCallBack(request);
        callBackMap.put(request.getInvokeId(), callBack);
        channel.writeAndFlush(request);
        return callBack;
    }
}
