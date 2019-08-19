package com.imspa.rpc.core;

import com.google.common.reflect.Reflection;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author Pann
 * @description Initialize & service interface wrapper & must write server addr
 * @date 2019-08-07 17:44
 */
public class RPCSendExecutor<T> implements FactoryBean {
    private Class<?> rpcInterface;
    private RPCServiceLoader loader = RPCServiceLoader.getInstance();

    public RPCSendExecutor(Class rpcInterface, String serverAddr) {
        this.rpcInterface = rpcInterface;
        loader.load(serverAddr);
    }

    public void stop() {
        loader.unLoad();
    }

    @Override
    public T getObject() throws Exception {
        return (T) Reflection.newProxy(rpcInterface, new RPCSendProxy());
    }

    @Override
    public Class<?> getObjectType() {
        return rpcInterface;
    }
}
