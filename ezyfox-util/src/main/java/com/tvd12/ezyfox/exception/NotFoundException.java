package com.tvd12.ezyfox.exception;

public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = 4266477686637543686L;

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(Throwable throwable) {
        super(throwable);
    }

    public NotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }}