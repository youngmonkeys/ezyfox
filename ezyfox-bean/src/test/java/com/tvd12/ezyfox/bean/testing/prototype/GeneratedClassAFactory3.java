package com.tvd12.ezyfox.bean.testing.prototype;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;
import com.tvd12.ezyfox.bean.impl.EzyByConstructorPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.impl.EzyPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.impl.EzySimpleBeanContext;
import com.tvd12.ezyfox.bean.impl.EzySimplePrototypeFactory;
import com.tvd12.ezyfox.reflect.EzyClass;

public class GeneratedClassAFactory3 {

    @Test
    public void test() throws Exception {
        EzyByConstructorPrototypeSupplierLoader.setDebug(false);

        EzyPrototypeSupplierLoader builder =
                new EzyByConstructorPrototypeSupplierLoader("classA", new EzyClass(ClassA.class));

        EzyPrototypeSupplier supplier = builder.load(new EzySimplePrototypeFactory());

        EzyBeanContext context = EzySimpleBeanContext.builder()
                .scan("com.tvd12.ezyfox.bean.testing.prototype")
                .build();
        System.out.println(supplier.getObjectType());
        ClassA classA = (ClassA) supplier.supply(context);
        System.out.println(classA.getClassB());
        classA = (ClassA) supplier.supply(context);
        System.out.println(classA.getClassB());
    }
}
