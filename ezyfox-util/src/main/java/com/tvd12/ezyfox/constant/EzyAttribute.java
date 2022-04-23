package com.tvd12.ezyfox.constant;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class EzyAttribute implements EzyConstant {

    protected int id;
    protected String name;

    private static final AtomicInteger COUNTER = new AtomicInteger(0);

    public EzyAttribute() {
        this(COUNTER.incrementAndGet());
    }

    public EzyAttribute(int id) {
        this(id, "attribute#" + id);
    }

    public EzyAttribute(String name) {
        this(COUNTER.incrementAndGet(), name);
    }

    public EzyAttribute(int id, String name) {
        this.id = id;
        this.name = name;
        COUNTER.incrementAndGet();
    }

    public static EzyAttribute one() {
        return new EzyAttribute();
    }

    public static EzyAttribute valueOf(int id) {
        return new EzyAttribute(id);
    }

    public static EzyAttribute valueOf(String name) {
        return new EzyAttribute(name);
    }

    public static EzyAttribute valueOf(int id, String name) {
        return new EzyAttribute(id, name);
    }
}
