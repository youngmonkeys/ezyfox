package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.annotation.EzyConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("rawtypes")
public class EzyConfigurationClassSorter {

    private EzyConfigurationClassSorter() {}

    public static List<Class> sort(Collection<Class> classes) {
        List<Class> list = new ArrayList<>(classes);
        list.sort(newComparator());
        return list;
    }

    private static Comparator<Class> newComparator() {
        return Comparator.comparingInt(EzyConfigurationClassSorter::getPriority);
    }

    private static int getPriority(Class<?> clazz) {
        return getPriority(clazz.getAnnotation(EzyConfiguration.class));
    }

    private static int getPriority(EzyConfiguration annotation) {
        int priority = 0;
        if (annotation != null) {
            priority = annotation.priority();
        }
        return priority;
    }
}
