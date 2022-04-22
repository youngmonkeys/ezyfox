package com.tvd12.ezyfox.exception;

public class EzyNotImplementedException extends RuntimeException {
    private static final long serialVersionUID = -8153551876705546785L;

    public EzyNotImplementedException() {
        super();
    }

    public EzyNotImplementedException(String msg) {
        super(msg);
    }

    public EzyNotImplementedException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
