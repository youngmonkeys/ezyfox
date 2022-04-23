package com.tvd12.ezyfox.exception;

public class UnimplementedOperationException extends RuntimeException {
    private static final long serialVersionUID = 7603927099427415774L;

    public UnimplementedOperationException(String msg, Throwable e) {
        super(msg, e);
    }
}
