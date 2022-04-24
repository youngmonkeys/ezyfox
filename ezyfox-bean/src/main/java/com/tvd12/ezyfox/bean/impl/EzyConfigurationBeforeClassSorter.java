package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("rawtypes")
public class EzyConfigurationBeforeClassSorter {

    private EzyConfigurationBeforeClassSorter() {}

    public static List<Class> sort(Collection<Class> classes) {
        List<Class> list = new ArrayList<>(classes);
        list.sort(newComparator());
        return list;
    }

    private static Comparator<Class> newComparator() {
        return Comparator.comparingInt(EzyConfigurationBeforeClassSorter::getPriority);
    }

    private static int getPriority(Class<?> clazz) {
        return getPriority(clazz.getAnnotation(EzyConfigurationBefore.class));
    }

    private static int getPriority(EzyConfigurationBefore annotation) {
        int priority = 0;
        if (annotation != null) {
            priority = annotation.priority();
        }
        return priority;
    }
}
