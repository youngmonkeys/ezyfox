package com.tvd12.ezyfox.bean.testing.prototype;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;
import com.tvd12.ezyfox.bean.impl.EzyByConstructorPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.impl.EzyPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.impl.EzySimpleBeanContext;
import com.tvd12.ezyfox.bean.impl.EzySimplePrototypeFactory;
import com.tvd12.ezyfox.reflect.EzyClass;
import org.testng.annotations.Test;

public class GeneratedClassAFactory {

    @Test
    public void test() throws Exception {
        EzyByConstructorPrototypeSupplierLoader.setDebug(true);

        EzyPrototypeSupplierLoader builder =
            new EzyByConstructorPrototypeSupplierLoader("classA", new EzyClass(ClassA.class));

        EzyPrototypeSupplier supplier = builder.load(new EzySimplePrototypeFactory());

        EzyBeanContext context = EzySimpleBeanContext.builder()
            .addPrototypeSupplier("classB", new EzyPrototypeSupplier() {

                @Override
                public Object supply(EzyBeanContext context) {
                    return new ClassB();
                }

                @Override
                public Class<?> getObjectType() {
                    return ClassB.class;
                }
            })
            .addPrototypeSupplier("classC", new EzyPrototypeSupplier() {

                @Override
                public Object supply(EzyBeanContext context) {
                    return new ClassC();
                }

                @Override
                public Class<?> getObjectType() {
                    return ClassC.class;
                }
            })
            .addPrototypeSupplier("classD", new EzyPrototypeSupplier() {

                @Override
                public Object supply(EzyBeanContext context) {
                    return new ClassD();
                }

                @Override
                public Class<?> getObjectType() {
                    return ClassD.class;
                }
            })
            .addPrototypeSupplier("classE", new EzyPrototypeSupplier() {

                @Override
                public Object supply(EzyBeanContext context) {
                    return new ClassE();
                }

                @Override
                public Class<?> getObjectType() {
                    return ClassE.class;
                }
            })
            .build();
        System.out.println(supplier.getObjectType());
        ClassA classA = (ClassA) supplier.supply(context);
        System.out.println(classA.getClassB());
    }
}
