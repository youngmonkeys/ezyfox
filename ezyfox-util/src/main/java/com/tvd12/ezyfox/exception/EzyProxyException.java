package com.tvd12.ezyfox.exception;

public class EzyProxyException extends RuntimeException {
    private static final long serialVersionUID = 877403999768900797L;

    public EzyProxyException(Exception e) {
        super(e);
    }
}
