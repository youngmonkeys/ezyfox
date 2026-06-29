package com.tvd12.ezyfox.binding.impl;

import com.tvd12.ezyfox.binding.annotation.EzyIgnore;
import com.tvd12.ezyfox.io.EzyLists;
import com.tvd12.ezyfox.io.EzyMaps;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.reflect.EzyMethods;
import com.tvd12.ezyfox.util.EzyLoggable;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tvd12.ezyfox.binding.EzyAccessType.DECLARED_FIELDS;
import static com.tvd12.ezyfox.binding.EzyAccessType.DECLARED_METHODS;
import static com.tvd12.ezyfox.binding.EzyAccessType.FIELDS;
import static com.tvd12.ezyfox.binding.EzyAccessType.METHODS;

public abstract class EzyAbstractElementsFetcher
    extends EzyLoggable
    implements EzyElementsFetcher {

    protected List<? extends EzyMethod> methodList;
    protected List<? extends EzyMethod> unoverriddenMethodList;
    protected Map<String, ? extends EzyMethod> methodsByFieldName;
    @Setter
    protected Map<Class<?>, Map<Class<?>, List<Object>>> elementsByClassCache;

    @Override
    public final List<Object> getElements(EzyClass clazz, int accessType) {
        Class<?> targetClass = clazz.getClazz();
        Map<Class<?>, List<Object>> byTarget = null;
        if (elementsByClassCache != null) {
            byTarget = elementsByClassCache.computeIfAbsent(
                this.getClass(),
                k -> new HashMap<>()
            );
            List<Object> cached = byTarget.get(targetClass);
            if (cached != null) {
                return cached;
            }
        }
        logger.debug("start scan {}", clazz);
        init(clazz);
        List<Object> elements = doGetElements(clazz, accessType);
        logger.debug("finish scan {}", clazz);
        if (byTarget != null) {
            byTarget.put(targetClass, elements);
        }
        return elements;
    }

    protected final void init(EzyClass clazz) {
        this.methodList = getMethodList(clazz);
        this.unoverriddenMethodList = filterOverriddenMethods(methodList);
        this.methodsByFieldName = mapMethodsByFieldName(unoverriddenMethodList);
    }

    protected abstract List<Object> doGetElements(EzyClass clazz, int accessType);

    protected final List<EzyField> getFields(EzyClass clazz, int accessType) {
        List<EzyField> fields = new ArrayList<>();
        if ((accessType & FIELDS) > 0) {
            fields.addAll(clazz.getFields());
        } else if ((accessType & DECLARED_FIELDS) > 0) {
            fields.addAll(clazz.getDeclaredFields());
        }
        return EzyLists.filter(fields, f -> !f.isAnnotated(EzyIgnore.class));
    }

    protected final List<? extends EzyMethod> getMethods(EzyClass clazz, int accessType) {
        List<EzyMethod> methods = new ArrayList<>();
        if ((accessType & METHODS) > 0) {
            methods.addAll(unoverriddenMethodList);
        } else if ((accessType & DECLARED_METHODS) > 0) {
            methods.addAll(getDeclaredMethods(clazz));
        }
        return EzyLists.filter(methods, m -> !isIgnoredMethod(m, clazz));
    }

    protected final List<? extends EzyMethod> getAnnotatedMethods(EzyClass clazz) {
        List<EzyMethod> methods0 = EzyLists.filter(
            clazz.getMethods(),
            this::shouldAddAnnotatedMethod
        );
        List<? extends EzyMethod> methods = filterOverriddenMethods(methods0);
        return EzyLists.newArrayList(methods, this::newByFieldMethod);
    }

    protected abstract boolean shouldAddAnnotatedMethod(EzyMethod method);

    protected abstract EzyMethod newByFieldMethod(EzyMethod method);

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected final List<? extends EzyMethod> filterOverriddenMethods(Collection allMethods) {
        return EzyMethods.filterOverriddenMethods(allMethods);
    }

    protected boolean isValidGenericField(EzyField field) {
        return true;
    }

    protected boolean isValidGenericMethod(EzyMethod method) {
        return true;
    }

    protected abstract List<? extends EzyMethod> getMethodList(EzyClass clazz);

    protected abstract List<? extends EzyMethod> getDeclaredMethods(EzyClass clazz);

    private Map<String, ? extends EzyMethod> mapMethodsByFieldName(
        List<? extends EzyMethod> methods
    ) {
        return EzyMaps.newHashMap(methods, EzyMethod::getFieldName);
    }

    private boolean isIgnoredMethod(EzyMethod method, EzyClass clazz) {
        boolean ignored = method.isAnnotated(EzyIgnore.class);
        if (ignored) {
            return true;
        }
        String fieldName = method.getFieldName();
        EzyField field = clazz.getField(fieldName);
        return field != null && field.isAnnotated(EzyIgnore.class);
    }
}
