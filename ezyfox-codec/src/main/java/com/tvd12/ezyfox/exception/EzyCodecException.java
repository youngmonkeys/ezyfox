package com.tvd12.ezyfox.exception;

public class EzyCodecException extends IllegalArgumentException {
    private static final long serialVersionUID = -1036617321776633153L;

    public EzyCodecException(String msg) {
        super(msg);
    }

    public EzyCodecException(String msg, Throwable e) {
        super(msg, e);
    }

}
