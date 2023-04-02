package com.tvd12.ezyfox.exception;

public class EzyMaxRequestSizeException extends IllegalArgumentException {
    private static final long serialVersionUID = -3982995135416662086L;

    public EzyMaxRequestSizeException(String msg) {
        super(msg);
    }

    public EzyMaxRequestSizeException(int size, int maxSize) {
        this("size = " + size + " when max size = " + maxSize);
    }
}
