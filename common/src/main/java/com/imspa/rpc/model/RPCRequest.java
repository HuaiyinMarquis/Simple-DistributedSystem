package com.imspa.rpc.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-07 16:26
 */
@Data
public class RPCRequest implements Serializable {
    private String invokeId;
    private String className;
    private String methodName;
    private Class[] typeParameters;
    private Object[] parametersVal;
}
