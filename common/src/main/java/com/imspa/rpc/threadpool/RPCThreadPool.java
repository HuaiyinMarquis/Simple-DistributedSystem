package com.imspa.rpc.threadpool;


import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-07 16:46
 */
public class RPCThreadPool {
    public static Executor getExecutor(int threads, int queues) {
        String name = "RPCSendThreadPool";
        return new ThreadPoolExecutor(threads, threads, 0, TimeUnit.MILLISECONDS,
                queues == 0 ? new SynchronousQueue<>()
                        : (queues < 0 ? new LinkedBlockingQueue<>()
                        : new LinkedBlockingQueue<>(queues)),
                new NamedThreadFactory(Boolean.TRUE), new AbortPolicyWithReport(name));
    }
}
