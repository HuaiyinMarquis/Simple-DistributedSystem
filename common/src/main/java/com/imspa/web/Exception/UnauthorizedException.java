package com.imspa.web.Exception;

/**
 * @author Pann
 * @description TODO
 * @date 2019-08-16 16:07
 */
public class UnauthorizedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String message) {
        super(message);
    }
}
