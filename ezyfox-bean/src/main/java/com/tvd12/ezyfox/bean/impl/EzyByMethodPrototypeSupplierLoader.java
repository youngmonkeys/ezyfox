package com.tvd12.ezyfox.bean.impl;

import java.util.List;
import java.util.Map;

import com.tvd12.ezyfox.asm.EzyFunction.EzyBody;
import com.tvd12.ezyfox.asm.EzyInstruction;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.ezyfox.reflect.EzyMethod;

public class EzyByMethodPrototypeSupplierLoader 
        extends EzySimplePrototypeSupplierLoader
        implements EzyPrototypeSupplierLoader {

    protected final EzyMethod method;
    protected final Object configurator;

    public EzyByMethodPrototypeSupplierLoader(
            String beanName, EzyMethod method, Object configurator) {
        super(beanName, new EzyClass(method.getReturnType()));
        this.method = method;
        this.configurator = configurator;
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected Map getAnnotationProperties() {
        return EzyKeyValueParser.getPrototypeProperties(
                method.getAnnotation(EzyPrototype.class));
    }

    @Override
    protected Class<?>[] getConstructorParameterTypes() {
        return method.getParameterTypes();
    }

    @Override
    protected EzyInstruction newConstructInstruction(EzyBody body, List<String> cparams) {
        Class<?> configClass = configurator.getClass();
        EzyInstruction prepare = newVariableInstruction(
                configClass, "configurator", EzyClasses.getVariableName(configClass));
        body.append(prepare);
        EzyInstruction instruction = new EzyInstruction("\t", "\n")
                .variable(clazz.getClazz(), "object")
                .equal()
                .append("configurator.")
                .append(method.getName())
                .bracketopen()
                .append(EzyStrings.join(cparams, ", "))
                .bracketclose();
        return instruction;
    }
}
