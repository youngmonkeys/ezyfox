package com.tvd12.ezyfox.binding.exception;

import lombok.Getter;

@Getter
public class EzyWriteValueException extends IllegalArgumentException {
    private static final long serialVersionUID = 5559113382041118309L;

    protected final Object value;
    protected final Class<?> outType;

    public EzyWriteValueException(Class<?> outType, Object value, Exception e) {
        super("can't write value: " + value + " to: " + outType.getName(), e);
        this.value = value;
        this.outType = outType;
    }
}