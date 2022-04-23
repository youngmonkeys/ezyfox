package com.tvd12.ezyfox.function;

import java.util.Arrays;
import java.util.function.Predicate;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class EzyPredicates {

    public static final Predicate ALWAYS_TRUE = o -> true;

    private EzyPredicates() {
    }

    public static <T> Predicate<T> alwaysTrue() {
        return ALWAYS_TRUE;
    }

    public static Predicate and(Iterable predicates) {
        return t -> {
            for (Object predicate : predicates) {
                if (!((Predicate) predicate).test(t)) {
                    return false;
                }
            }
            return true;
        };
    }

    public static Predicate or(Predicate... predicates) {
        return or(Arrays.asList(predicates));
    }

    public static Predicate or(Iterable predicates) {
        return t -> {
            for (Object predicate : predicates) {
                if (((Predicate) predicate).test(t)) {
                    return true;
                }
            }
            return false;
        };
    }
}
