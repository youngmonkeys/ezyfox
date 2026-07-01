package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.annotation.EzyProperty;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPostInit;
import com.tvd12.ezyfox.io.EzyLists;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.reflect.EzyMethods;
import com.tvd12.ezyfox.reflect.EzySetterMethod;
import com.tvd12.properties.file.annotation.Property;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public final class EzyObjectBuilderMetadata {

    private final List<EzyField> bindingFields;
    private final List<EzySetterMethod> bindingMethods;
    private final List<EzyField> propertyFields;
    private final List<EzySetterMethod> propertyMethods;
    private final List<EzyField> missingBindingSetterFields;
    private final List<EzyField> missingPropertySetterFields;
    private final List<EzyMethod> postInitMethods;

    private static final List<Class<? extends Annotation>> BINDING_ANNOTATIONS =
        Collections.singletonList(EzyAutoBind.class);
    private static final List<Class<? extends Annotation>> PROPERTY_ANNOTATIONS =
        Arrays.asList(Property.class, EzyProperty.class);

    public EzyObjectBuilderMetadata(
        EzyClass clazz,
        boolean addMissingSetterFields
    ) {
        this.missingBindingSetterFields = getMissingSetterFields(
            clazz,
            BINDING_ANNOTATIONS
        );
        this.missingPropertySetterFields = getMissingSetterFields(
            clazz,
            PROPERTY_ANNOTATIONS
        );
        this.bindingFields = getFields(
            getValidFields(clazz, BINDING_ANNOTATIONS),
            missingBindingSetterFields,
            addMissingSetterFields
        );
        this.bindingMethods = getValidMethods(
            clazz,
            method -> isValidMethod(clazz, method, BINDING_ANNOTATIONS)
        );
        this.propertyFields = getFields(
            getValidFields(clazz, PROPERTY_ANNOTATIONS),
            missingPropertySetterFields,
            addMissingSetterFields
        );
        this.propertyMethods = getValidMethods(
            clazz,
            method -> isValidMethod(clazz, method, PROPERTY_ANNOTATIONS)
        );
        this.postInitMethods = clazz.getPublicMethods(m ->
            m.isAnnotated(EzyPostInit.class)
                && m.getParameterCount() == 0
        );
    }

    public List<EzyField> getBindingFields() {
        return new ArrayList<>(bindingFields);
    }

    public List<EzySetterMethod> getBindingMethods() {
        return new ArrayList<>(bindingMethods);
    }

    public List<EzyField> getPropertyFields() {
        return new ArrayList<>(propertyFields);
    }

    public List<EzySetterMethod> getPropertyMethods() {
        return new ArrayList<>(propertyMethods);
    }

    public List<EzyField> getMissingBindingSetterFields() {
        return new ArrayList<>(missingBindingSetterFields);
    }

    public List<EzyField> getMissingPropertySetterFields() {
        return new ArrayList<>(missingPropertySetterFields);
    }

    public List<EzyMethod> getPostInitMethods() {
        return new ArrayList<>(postInitMethods);
    }

    private static List<EzyField> getFields(
        List<EzyField> validFields,
        List<EzyField> missingSetterFields,
        boolean addMissingSetterFields
    ) {
        List<EzyField> fields = new ArrayList<>(validFields);
        if (addMissingSetterFields) {
            fields.addAll(missingSetterFields);
        }
        return fields;
    }

    private static List<EzyField> getValidFields(
        EzyClass clazz,
        List<Class<? extends Annotation>> annotations
    ) {
        return clazz.getFields(f ->
            f.isPublic() && isAnnotated(f, annotations)
        );
    }

    private static List<EzySetterMethod> getValidMethods(
        EzyClass clazz,
        Predicate<EzyMethod> predicate
    ) {
        List<EzyMethod> methods = clazz.getMethods();
        List<EzyMethod> valid0 = EzyLists
            .filter(methods, predicate);
        List<EzyMethod> valid = EzyMethods
            .filterOverriddenMethods(valid0);
        return EzyLists.newArrayList(valid, EzySetterMethod::new);
    }

    private static boolean isValidMethod(
        EzyClass clazz,
        EzyMethod method,
        List<Class<? extends Annotation>> annotations
    ) {
        if (method.getParameterCount() != 1) {
            return false;
        }
        EzyField field = clazz.getField(method.getFieldName());
        boolean answer = field != null
            && !field.isPublic()
            && isAnnotated(field, annotations);
        return answer || isAnnotated(method, annotations);
    }

    private static List<EzyField> getMissingSetterFields(
        EzyClass clazz,
        List<Class<? extends Annotation>> annotationClasses
    ) {
        return clazz.getFields(f -> {
            if (!isAnnotated(f, annotationClasses)) {
                return false;
            }
            if (f.isPublic()) {
                return false;
            }
            EzyMethod setter = clazz
                .getSetterMethod(f.getSetterMethod());
            return setter == null;
        });
    }

    private static boolean isAnnotated(
        EzyField field,
        List<Class<? extends Annotation>> annotations
    ) {
        for (Class<? extends Annotation> ann : annotations) {
            if (field.isAnnotated(ann)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAnnotated(
        EzyMethod method,
        List<Class<? extends Annotation>> annotations
    ) {
        for (Class<? extends Annotation> ann : annotations) {
            if (method.isAnnotated(ann)) {
                return true;
            }
        }
        return false;
    }
}
