package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.asm.EzyFunction.EzyBody;
import com.tvd12.ezyfox.asm.EzyInstruction;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.ezyfox.reflect.EzyField;

import java.util.List;
import java.util.Map;

public class EzyByFieldPrototypeSupplierLoader
    extends EzySimplePrototypeSupplierLoader
    implements EzyPrototypeSupplierLoader {

    protected final EzyField field;
    protected final Object configurator;

    public EzyByFieldPrototypeSupplierLoader(
        String beanName, EzyField field, Object configurator) {
        super(beanName, new EzyClass(field.getType()));
        this.field = field;
        this.configurator = configurator;
    }

    @Override
    protected Class<?>[] getConstructorParameterTypes() {
        return new Class[0];
    }

    @Override
    protected String[] getConstructorArgumentNames() {
        return new String[0];
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected Map getAnnotationProperties() {
        return EzyKeyValueParser.getPrototypeProperties(
            field.getAnnotation(EzyPrototype.class));
    }

    @Override
    protected EzyInstruction newConstructInstruction(EzyBody body, List<String> cparams) {
        Class<?> configClass = configurator.getClass();
        EzyInstruction prepare = newVariableInstruction(
            configClass, "configurator", EzyClasses.getVariableName(configClass));
        body.append(prepare);
        return new EzyInstruction("\t", "\n")
            .variable(clazz.getClazz(), "object")
            .equal()
            .append("configurator.")
            .append(field.getName());
    }
}
