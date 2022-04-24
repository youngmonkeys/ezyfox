package com.tvd12.ezyfox.bean.testing.prototype;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;
import org.testng.annotations.Test;

public class BeanContextTest {

    @Test
    public void test() {
        EzyBeanContext ctx = EzyBeanContext.builder()
            .scan("com.tvd12.ezyfox.bean.testing.prototype")
            .build();
        EzyPrototypeSupplier supplier = ctx.getPrototypeFactory().getSupplier(ClassA.class);
        ClassA a = (ClassA) supplier.supply(ctx);
        System.out.println(a);
    }
}
