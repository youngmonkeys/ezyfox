package com.tvd12.ezyfox.exception;

public class EzyTimeoutException extends RuntimeException {
    private static final long serialVersionUID = 3641888970130069679L;

    public EzyTimeoutException(String msg) {
        super(msg);
    }

    public EzyTimeoutException(String msg, Exception e) {
        super(msg, e);
    }
}
