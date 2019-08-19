package com.imspa.rpc.core;

import com.imspa.rpc.model.RPCRequest;
import com.imspa.rpc.model.RPCResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-07 22:06
 */
public class RPCRecvInitializeTask implements Runnable {
    private static final Logger logger = LogManager.getLogger(RPCRecvInitializeTask.class);
    private RPCRequest request;
    private RPCResponse response;
    private Map<String, Object> handlerMap;
    private ChannelHandlerContext ctx;

    public RPCResponse getResponse() {
        return response;
    }

    public RPCRequest getRequest() {
        return request;
    }

    public void setRequest(RPCRequest request) {
        this.request = request;
    }

    RPCRecvInitializeTask(RPCRequest request, RPCResponse response, Map<String, Object> handlerMap, ChannelHandlerContext ctx) {
        this.request = request;
        this.response = response;
        this.handlerMap = handlerMap;
        this.ctx = ctx;
    }

    @Override
    public void run() {
        response.setInvokeId(request.getInvokeId());
        try {
            Object result = reflect(request);
            response.setResultVal(result);
        } catch (Throwable t) {
            response.setErrorMessage(t.toString());
            logger.debug("RPC Service invoke error! request:{}", request);
        }

        ctx.writeAndFlush(response).addListener((ChannelFutureListener) channelFuture -> logger.info("RPC Service send response:{}", request.getInvokeId())
        ); //TODO log
    }

    private Object reflect(RPCRequest request) throws Throwable {
        String className = request.getClassName();
        Object serviceBean = handlerMap.get(className);
        String methodName = request.getMethodName();
        Object[] parameters = request.getParametersVal();
        Method method = Class.forName(className).getMethod(methodName, request.getTypeParameters());

        return method.invoke(serviceBean, parameters);
    }
}
