package com.tvd12.ezyfox.constant;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class EzyAttribute<T> implements EzyConstant {

    private static final AtomicInteger COUNTER = new AtomicInteger(0);
    protected int id;
    protected String name;

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

    public static <T> EzyAttribute<T> one() {
        return new EzyAttribute<>();
    }

    public static <T> EzyAttribute<T> valueOf(int id) {
        return new EzyAttribute<>(id);
    }

    public static <T> EzyAttribute<T> valueOf(String name) {
        return new EzyAttribute<>(name);
    }

    public static <T> EzyAttribute<T> valueOf(int id, String name) {
        return new EzyAttribute<>(id, name);
    }
}
