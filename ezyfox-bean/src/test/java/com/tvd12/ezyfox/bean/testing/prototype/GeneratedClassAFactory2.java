package com.tvd12.ezyfox.bean.testing.prototype;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;
import com.tvd12.ezyfox.bean.impl.*;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

public class GeneratedClassAFactory2 {

    @Test
    public void test() throws Exception {
        EzyByConstructorPrototypeSupplierLoader.setDebug(true);
        EzyByFieldPrototypeSupplierLoader.setDebug(true);
        EzyByMethodPrototypeSupplierLoader.setDebug(true);

        EzyPrototypeSupplierLoader builder =
            new EzyByConstructorPrototypeSupplierLoader("classA", new EzyClass(ClassA.class));

        EzyPrototypeSupplier supplier = builder.load(new EzySimplePrototypeFactory());

        EzyBeanContext context = EzySimpleBeanContext.builder()
            .addProperty("game.auto_start", "true")
            .addProperty("game.byte", "1")
            .addProperty("game.char", "a")
            .addProperty("game.double", "2.0")
            .addProperty("game.float", "3.0")
            .addProperty("game.int", "4")
            .addProperty("game.long", "5")
            .addProperty("game.short", "6")
            .scan("com.tvd12.ezyfox.bean.testing.prototype")
            .build();
        System.out.println(supplier.getObjectType());
        ClassA classA = (ClassA) supplier.supply(context);
        System.out.println(classA);
        System.out.println(classA.getClassB());
        classA = (ClassA) supplier.supply(context);
        System.out.println(classA.getClassB());

        Asserts.assertTrue(classA.isAutoStartField());
    }
}
