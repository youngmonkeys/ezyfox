package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.annotation.EzyProperty;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyPostInit;
import com.tvd12.ezyfox.bean.exception.EzyMissingSetterException;
import com.tvd12.ezyfox.io.EzyLists;
import com.tvd12.ezyfox.reflect.*;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfox.util.EzyPropertyAnnotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

@SuppressWarnings("rawtypes")
public abstract class EzySimpleObjectBuilder extends EzyLoggable {

    protected final EzyClass clazz;
    protected final String beanName;
    protected final AtomicInteger variableCount;
    protected final List<EzyField> bindingFields;
    protected final List<EzySetterMethod> bindingMethods;
    protected final List<EzyField> propertyFields;
    protected final List<EzySetterMethod> propertyMethods;

    public EzySimpleObjectBuilder(String beanName, EzyClass clazz) {
        this.clazz = clazz;
        this.beanName = beanName;
        this.variableCount = new AtomicInteger(0);
        this.bindingFields = getBindingFields(clazz);
        this.bindingMethods = getBindingMethods(clazz);
        this.propertyFields = getPropertyFields(clazz);
        this.propertyMethods = getPropertyMethods(clazz);
        this.checkMissingSetterMethodFields(bindingFields, EzyAutoBind.class);
        this.checkMissingSetterMethodFields(propertyFields, EzyProperty.class);
    }

    protected Constructor getConstructor(EzyClass clazz) {
        List<Constructor> constructors = clazz.getDeclaredConstructors();
        for (Constructor con : constructors) {
            if (con.isAnnotationPresent(EzyAutoBind.class)) {
                return con;
            }
        }
        Constructor con = clazz.getNoArgsDeclaredConstructor();
        if (con == null) {
            con = clazz.getMaxArgsDeclaredConstructor();
        }
        return con;
    }

    protected abstract Class<?>[] getConstructorParameterTypes();

    protected String[] getConstructorArgumentNames() {
        return getArgumentNames(getConstructorParameterTypes());
    }

    protected final String[] getArgumentNames(Class<?>[] parameterTypes) {
        String[] names = new String[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            names[i] = EzyClasses.getVariableName(parameterTypes[i]);
        }
        return names;
    }

    protected final String[] getConstructorArgumentNames(EzyAutoBind annotation) {
        Class<?>[] parameterTypes = getConstructorParameterTypes();
        String[] names = getArgumentNames(parameterTypes);
        if (annotation == null) {
            return names;
        }
        String[] fixNames = annotation.value();
        for (int i = 0; i < fixNames.length; i++) {
            if (i < names.length) {
                names[i] = fixNames[i];
            }
        }
        return names;
    }

    protected final String[] getConstructorArgumentNames(Constructor<?> constructor) {
        return getConstructorArgumentNames(
            constructor.getAnnotation(EzyAutoBind.class));
    }

    protected final List<EzyField> getBindingFields(EzyClass clazz) {
        return getValidFields(clazz, EzyAutoBind.class);
    }

    protected final List<EzySetterMethod> getBindingMethods(EzyClass clazz) {
        return getValidMethods(clazz, this::isBindingMethod);
    }

    protected final List<EzyField> getPropertyFields(EzyClass clazz) {
        return getValidFields(clazz, EzyProperty.class);
    }

    protected final List<EzySetterMethod> getPropertyMethods(EzyClass clazz) {
        return getValidMethods(clazz, this::isPropertyMethod);
    }

    protected final List<EzyMethod> getPostInitMethods() {
        return clazz.getPublicMethods(m ->
            m.isAnnotated(EzyPostInit.class) &&
                m.getParameterCount() == 0
        );
    }

    private boolean isBindingMethod(EzyMethod method) {
        return isValidMethod(method, EzyAutoBind.class);
    }

    private boolean isPropertyMethod(EzyMethod method) {
        return isValidMethod(method, EzyProperty.class);
    }

    private List<EzyField> getValidFields(
        EzyClass clazz, Class<? extends Annotation> ann) {
        return clazz.getFields(f -> f.isPublic() && f.isAnnotated(ann));
    }

    private List<EzySetterMethod>
    getValidMethods(EzyClass clazz, Predicate<EzyMethod> predicate) {
        List<EzyMethod> methods = clazz.getMethods();
        List<EzyMethod> valid0 = EzyLists.filter(methods, predicate);
        List<EzyMethod> valid = EzyMethods.filterOverriddenMethods(valid0);
        return EzyLists.newArrayList(valid, EzySetterMethod::new);
    }

    private boolean isValidMethod(EzyMethod method, Class<? extends Annotation> ann) {
        if (method.getParameterCount() != 1) {
            return false;
        }
        EzyField field = clazz.getField(method.getFieldName());
        boolean answer =
            field != null &&
                !field.isPublic() &&
                field.isAnnotated(ann);
        return answer || method.isAnnotated(ann);
    }

    protected final boolean isAbstractClass(Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    protected final String getBeanName(EzyReflectElement element) {
        if (element instanceof EzyField) {
            return getBeanName((EzyField) element);
        }
        return getBeanName((EzyMethod) element);
    }

    private String getBeanName(EzyField field) {
        EzyAutoBind annotation = field.getAnnotation(EzyAutoBind.class);
        if (annotation == null) {
            return field.getName();
        }
        if (annotation.value().length > 0 && annotation.value()[0].length() > 0) {
            return annotation.value()[0];
        }
        return field.getName();
    }

    private String getBeanName(EzyMethod method) {
        EzyAutoBind annotation = method.getAnnotation(EzyAutoBind.class);
        if (annotation != null && annotation.value().length > 0) {
            return annotation.value()[0];
        }
        String fieldName = method.getFieldName();
        EzyField field = clazz.getField(fieldName);
        return field != null ? getBeanName(field) : fieldName;
    }

    protected final String getPropertyName(EzyReflectElement element) {
        return EzyPropertyAnnotations.getPropertyName(clazz, element);
    }

    private void checkMissingSetterMethodFields(
        List<EzyField> fields, Class<? extends Annotation> annClass) {
        List<EzyField> missingSetterFields = clazz.getFields(f -> {
            if (!f.isAnnotated(annClass)) {
                return false;
            }
            if (f.isPublic()) {
                return false;
            }
            EzyMethod setter = clazz.getSetterMethod(f.getSetterMethod());
            return setter == null;
        });
        boolean addMissingSetterFields = addMissingSetterFields();
        for (EzyField field : missingSetterFields) {
            if (addMissingSetterFields) {
                fields.add(field);
            } else {
                logger.warn("field: {} maybe null", field.getName(), new EzyMissingSetterException(field));
            }
        }
    }

    protected boolean addMissingSetterFields() {
        return true;
    }
}
