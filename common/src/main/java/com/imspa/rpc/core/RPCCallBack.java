package com.imspa.rpc.core;

import com.imspa.rpc.model.RPCRequest;
import com.imspa.rpc.model.RPCResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-07 22:09
 */
public class RPCCallBack {
    private static final Logger logger = LogManager.getLogger(RPCCallBack.class);

    private RPCRequest request;
    private RPCResponse response;
    private Lock lock = new ReentrantLock();
    private Condition finish = lock.newCondition();

    public RPCCallBack(RPCRequest request) {
        this.request = request;
    }

    public Object start() throws InterruptedException {
        try {
            lock.lock();
            finish.await(3 * 1000, TimeUnit.MILLISECONDS);
            if (this.response != null) {
                logger.debug("RPC Client recv response:{}", response);
                return this.response.getResultVal();
            } else {
                logger.warn("RPC Client recv Response timeout(more than 3 sec), The request:{}, ", request);
                return null;
            }
        } finally {
            lock.unlock();
        }
    }

    public void over(RPCResponse response) {
        try {
            lock.lock();
            finish.signal();
            this.response = response;
        } finally {
            lock.unlock();
        }
    }
}
