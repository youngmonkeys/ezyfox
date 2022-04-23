package com.tvd12.ezyfox.exception;

import lombok.Getter;

@Getter
public class EzyArrayGetException extends IllegalArgumentException {
    private static final long serialVersionUID = -5250894414568092276L;

    protected final int index;
    protected final Object value;
    protected final Class<?> outType;

    public EzyArrayGetException(
        int index,
        Object value,
        Class<?> outType,
        Exception e
    ) {
        super(
            "can't transform value: " + value +
                " at: " + index + " with outType: " + outType.getName(),
            e
        );
        this.index = index;
        this.value = value;
        this.outType = outType;
    }
}
