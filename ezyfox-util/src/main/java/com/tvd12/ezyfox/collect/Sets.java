package com.tvd12.ezyfox.collect;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public final class Sets {

    private Sets() {}

    public static <T> Set<T> newHashSet(T... args) {
        return new HashSet<>(Arrays.asList(args));
    }

    public static <T> Set<T> newHashSet(Iterable<T> iterable) {
        Set<T> set = new HashSet<>();
        for (T t : iterable) {
            set.add(t);
        }
        return set;
    }

    public static <T> Set<T> toSet(Iterable<T> iterable) {
        if (iterable instanceof Set) {
            return (Set<T>) iterable;
        }
        return newHashSet(iterable);
    }
}
