package com.tvd12.ezyfox.reflect;

import static com.tvd12.ezyfox.reflect.EzyReflections.METHOD_PREFIX_GET;
import static com.tvd12.ezyfox.reflect.EzyReflections.METHOD_PREFIX_IS;
import static com.tvd12.ezyfox.reflect.EzyReflections.METHOD_PREFIX_SET;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tvd12.ezyfox.collect.Lists;

public final class EzyFields {

    private EzyFields() {}

    public static Object get(Field field, Object obj) {
        try {
            return field.get(obj);
        }
        catch(Exception e) {
            throw new IllegalArgumentException("can't get value from field " + field.getName(), e);
        }
    }

    public static void set(Field field, Object obj, Object value) {
        try {
            field.set(obj, value);
        }
        catch(Exception e) {
            throw new IllegalArgumentException("can't set value to field " + field.getName(), e);
        }
    }

    @SuppressWarnings("rawtypes")
    public static List<Field> getFields(Class clazz) {
        List<Field> answer = new ArrayList<>();
        Class currentClass = clazz;
        while(currentClass != null && !currentClass.equals(Object.class)) {
            Field[] fields = currentClass.getDeclaredFields();
            Collections.addAll(answer, fields);
            currentClass = currentClass.getSuperclass();
        }
        return answer;
    }

    @SuppressWarnings("rawtypes")
    public static Field getField(Class clazz, String fieldName) {
        return new EzyFieldFinder(clazz, fieldName).find();
    }

    @SuppressWarnings("rawtypes")
    public static List<Field> getAnnotatedFields(
            Class clazz, Class<? extends Annotation> annClass) {
        List<Field> answer = new ArrayList<>();
        Class currentClass = clazz;
        while(!currentClass.equals(Object.class)) {
            Field[] fields = currentClass.getDeclaredFields();
            for(Field field : fields) {
                if(field.isAnnotationPresent(annClass))
                    answer.add(field);
            }
            currentClass = currentClass.getSuperclass();
        }
        return answer;
    }

    @SuppressWarnings("rawtypes")
    public static List<Field> getDeclaredFields(Class clazz) {
        return Lists.newArrayList(clazz.getDeclaredFields());
    }

    public static String getGetterMethod(Field field) {
        Class<?> type = field.getType();
        String prefix = METHOD_PREFIX_GET;
        if(type.equals(boolean.class))
            prefix = METHOD_PREFIX_IS;
        return prefix + getMethodSuffix(field);
    }
    
    public static String getSetterMethod(Field field) {
        return METHOD_PREFIX_SET + getMethodSuffix(field);
    }
    
    private static String getMethodSuffix(Field field) {
        String name = field.getName();
        String first = name.substring(0, 1).toUpperCase();
        return name.length() == 1 ? first : first + name.substring(1);
    }}