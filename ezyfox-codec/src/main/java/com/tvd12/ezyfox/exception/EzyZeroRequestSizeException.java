package com.tvd12.ezyfox.exception;

public class EzyZeroRequestSizeException extends IllegalArgumentException {
    private static final long serialVersionUID = -3982995135416662086L;

    public EzyZeroRequestSizeException() {
        this("zero request size");
    }

    public EzyZeroRequestSizeException(String msg) {
        super(msg);
    }
}
