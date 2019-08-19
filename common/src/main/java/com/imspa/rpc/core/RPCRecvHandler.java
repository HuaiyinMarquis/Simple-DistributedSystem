package com.imspa.rpc.core;

import com.imspa.rpc.model.RPCRequest;
import com.imspa.rpc.model.RPCResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-07 18:42
 */
public class RPCRecvHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LogManager.getLogger(RPCRecvHandler.class);
    private final Map<String, Object> handlerMap;

    public RPCRecvHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg.getClass().equals(String.class)) {
            logger.debug("RPC Service get String Class request(HEART_BEAT):{}", msg);
            ctx.writeAndFlush(msg + "1");
        } else {
            RPCRequest request = (RPCRequest) msg;
            RPCResponse response = new RPCResponse();
            RPCRecvInitializeTask recvTask = new RPCRecvInitializeTask(request, response, handlerMap, ctx);
            RPCRecvExecutor.submit(recvTask);
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
