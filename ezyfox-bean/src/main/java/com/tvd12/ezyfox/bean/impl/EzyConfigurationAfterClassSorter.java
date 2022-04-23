package com.tvd12.ezyfox.bean.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import com.tvd12.ezyfox.bean.annotation.EzyConfigurationAfter;

@SuppressWarnings("rawtypes")
public class EzyConfigurationAfterClassSorter {

    private EzyConfigurationAfterClassSorter() {
    }

    public static List<Class> sort(Collection<Class> classes) {
        List<Class> list = new ArrayList<>(classes);
        list.sort(newComparator());
        return list;
    }

    private static Comparator<Class> newComparator() {
        return (c1, c2) -> getPriority(c1) - getPriority(c2);
    }

    private static int getPriority(Class<?> clazz) {
        return getPriority(clazz.getAnnotation(EzyConfigurationAfter.class));
    }

    private static int getPriority(EzyConfigurationAfter annotation) {
        int priority = 0;
        if(annotation != null)
            priority = annotation.priority();
        return priority;
    }
}
