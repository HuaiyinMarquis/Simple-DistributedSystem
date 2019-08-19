package com.imspa.rpc.threadpool;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-07 17:19
 */
public class AbortPolicyWithReport extends ThreadPoolExecutor.AbortPolicy {
    private String threadPoolName;

    public AbortPolicyWithReport(String threadPoolName) {
        this.threadPoolName = threadPoolName;
    }

    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        throw new RejectedExecutionException("Task " + r.toString() +
                " rejected from " +
                e.toString());
    }
}
