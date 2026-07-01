package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.exception.EzyMissingSetterException;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.reflect.EzyReflectElement;
import com.tvd12.ezyfox.reflect.EzySetterMethod;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyfox.util.EzyPropertyAnnotations;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("rawtypes")
public abstract class EzySimpleObjectBuilder extends EzyLoggable {

    protected final EzyClass clazz;
    protected final String beanName;
    protected final AtomicInteger variableCount;
    protected final List<EzyField> bindingFields;
    protected final List<EzySetterMethod> bindingMethods;
    protected final List<EzyField> propertyFields;
    protected final List<EzySetterMethod> propertyMethods;
    protected final EzyBeanMetadataCache metadataCache;

    public EzySimpleObjectBuilder(
        String beanName,
        EzyClass clazz,
        EzyBeanMetadataCache metadataCache
    ) {
        this.clazz = clazz;
        this.beanName = beanName;
        this.metadataCache = metadataCache;
        this.variableCount = new AtomicInteger(0);
        EzyObjectBuilderMetadata metadata = metadataCache.getObjectBuilder(
            clazz.getClazz(),
            addMissingSetterFields()
        );
        this.bindingFields = metadata.getBindingFields();
        this.bindingMethods = metadata.getBindingMethods();
        this.propertyFields = metadata.getPropertyFields();
        this.propertyMethods = metadata.getPropertyMethods();
        this.logMissingSetterMethodFields(
            metadata.getMissingBindingSetterFields()
        );
        this.logMissingSetterMethodFields(
            metadata.getMissingPropertySetterFields()
        );
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

    protected final String[] getArgumentNames(Class<?>[] parameterTypes) {
        String[] names = new String[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            names[i] = EzyClasses.getVariableName(parameterTypes[i]);
        }
        return names;
    }

    protected String[] getConstructorArgumentNames() {
        return getArgumentNames(getConstructorParameterTypes());
    }

    protected final String[] getConstructorArgumentNames(
        EzyAutoBind annotation
    ) {
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

    protected final String[] getConstructorArgumentNames(
        Constructor<?> constructor
    ) {
        return getConstructorArgumentNames(
            constructor.getAnnotation(EzyAutoBind.class)
        );
    }

    protected final List<EzyMethod> getPostInitMethods() {
        return metadataCache.getObjectBuilder(
            clazz.getClazz(),
            addMissingSetterFields()
        ).getPostInitMethods();
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
        if (annotation.value().length > 0 && !annotation.value()[0].isEmpty()) {
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

    private void logMissingSetterMethodFields(List<EzyField> fields) {
        if (addMissingSetterFields()) {
            return;
        }
        for (EzyField field : fields) {
            logger.warn(
                "field: {} maybe null",
                field.getName(),
                new EzyMissingSetterException(field)
            );
        }
    }

    protected boolean addMissingSetterFields() {
        return true;
    }
}
