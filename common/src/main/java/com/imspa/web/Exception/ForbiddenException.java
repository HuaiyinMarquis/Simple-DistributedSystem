package com.imspa.web.Exception;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-16 16:06
 */
public class ForbiddenException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ForbiddenException(String message) {
        super(message);
    }
}
