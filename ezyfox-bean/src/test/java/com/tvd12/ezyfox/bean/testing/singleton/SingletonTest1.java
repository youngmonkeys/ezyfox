package com.tvd12.ezyfox.bean.testing.singleton;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.impl.EzyByConstructorPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.impl.EzySimpleBeanContext;

public class SingletonTest1 {

    @Test
    public void test() throws Exception {
        EzyByConstructorPrototypeSupplierLoader.setDebug(true);

        EzyBeanContext context = EzySimpleBeanContext.builder()
                .scan("com.tvd12.ezyfox.bean.testing.singleton")
                .build();
        context.getBean("combine", Combine.class);

        Combine combine = (Combine) context.getBean("combine", Combine.class);
        ClassA classA = (ClassA) context.getBean("a", ClassA.class);
        assert classA != null;
        assert combine.getClassA() != null;
        assert combine.getClassA() == classA;

        XClassA xClassA = (XClassA) context.getBean("xa", XClassA.class);
        assert xClassA != null;
        assert combine.getXClassA() != null;
        assert combine.getXClassA() == xClassA;
    }
}