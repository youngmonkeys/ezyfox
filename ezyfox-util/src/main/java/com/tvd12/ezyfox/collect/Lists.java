package com.tvd12.ezyfox.collect;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class Lists {

    private Lists() {
    }

    public static <T> List<T> newArrayList(T... args) {
        List<T> list = new ArrayList<>();
        for(T t : args)
            list.add(t);
        return list;
    }

    public static <T> List<T> newArrayList(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        for(T t : iterable)
            list.add(t);
        return list;
    }

    public static <T> List<T> tryNewArrayList(Iterable<T> iterable) {
        if(iterable instanceof List)
            return (List<T>)iterable;
        return newArrayList(iterable);
    }

    public static <T> List<T> toList(Iterable<T> iterable) {
        if(iterable instanceof List)
            return (List<T>)iterable;
        return newArrayList(iterable);
    }
}
