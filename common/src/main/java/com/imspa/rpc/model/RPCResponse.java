package com.imspa.rpc.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-07 16:29
 */
@Data
public class RPCResponse implements Serializable {
    private String invokeId;
    private String errorMessage;
    private Object resultVal;
}
