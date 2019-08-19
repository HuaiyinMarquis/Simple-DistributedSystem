package com.imspa.rpc.core;

import com.google.common.reflect.AbstractInvocationHandler;
import com.imspa.rpc.model.RPCRequest;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @author Pann
 * @description
 * @date 2019-08-07 17:31
 */
public class RPCSendProxy extends AbstractInvocationHandler {
    @Override
    protected Object handleInvocation(Object o, Method method, Object[] objects) throws Throwable {
        RPCRequest request = new RPCRequest();
        request.setInvokeId(UUID.randomUUID().toString()); //TODO can use other method
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setTypeParameters(method.getParameterTypes());
        request.setParametersVal(objects);

        RPCSendHandler handler = RPCServiceLoader.getInstance().getMessageSendHandler();
        RPCCallBack callBack = handler.sendRequest(request);
        return callBack.start();
    }
}
