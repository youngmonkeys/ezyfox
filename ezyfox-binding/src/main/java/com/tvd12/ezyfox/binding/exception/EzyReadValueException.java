package com.tvd12.ezyfox.binding.exception;

import lombok.Getter;

@Getter
public class EzyReadValueException extends IllegalArgumentException {
    private static final long serialVersionUID = 5559113382041118309L;

    protected final Object value;
    protected final Class<?> outType;

    public EzyReadValueException(Class<?> outType, Object value, Exception e) {
        super("can't read value: " + value + " to: " + outType.getName(), e);
        this.value = value;
        this.outType = outType;
    }
}
