package com.tvd12.ezyfox.util;

public class EzyHashCodes {

    protected final int initial;
    protected final int prime;
    protected int hashCode;

    public EzyHashCodes() {
        this(1, 31);
    }

    public EzyHashCodes(int initial, int prime) {
        this.initial = initial;
        this.prime = prime;
        this.hashCode = initial;
    }

    public int toHashCode() {
        return hashCode;
    }

    public EzyHashCodes append(Object value) {
        this.hashCode = hashCode * prime + (value == null ? 43 : value.hashCode());
        return this;
    }
}
