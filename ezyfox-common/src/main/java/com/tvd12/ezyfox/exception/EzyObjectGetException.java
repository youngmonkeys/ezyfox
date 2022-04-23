package com.tvd12.ezyfox.exception;

import lombok.Getter;

@Getter
public class EzyObjectGetException extends IllegalArgumentException {
    private static final long serialVersionUID = -5250894414568092276L;

    protected final Object key;
    protected final Object value;
    protected final Class<?> outType;

    public EzyObjectGetException(
        Object key,
        Object value,
        Class<?> outType,
        Exception e
    ) {
        super(
            "can't transform value: " + value +
                " of key: " + key + " with outType: " + outType.getName(),
            e
        );
        this.key = key;
        this.value = value;
        this.outType = outType;
    }
}
