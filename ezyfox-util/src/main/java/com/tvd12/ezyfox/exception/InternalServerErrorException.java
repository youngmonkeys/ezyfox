package com.tvd12.ezyfox.exception;

public class InternalServerErrorException extends RuntimeException {
    private static final long serialVersionUID = 6702635317131062317L;

    public InternalServerErrorException() {
        super();
    }

    public InternalServerErrorException(String msg) {
        super(msg);
    }

    public InternalServerErrorException(Exception e) {
        super("server error", e);
    }

    public InternalServerErrorException(String msg, Exception e) {
        super(msg, e);
    }
}
