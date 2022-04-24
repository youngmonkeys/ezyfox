package com.tvd12.ezyfox.bean.testing.complex;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import org.testng.annotations.Test;

public class ComplexTest {

    @Test
    public void test() {
        EzyBeanContext.builder()
            .addSingletonClass(ClassAImpl.class)
            .addSingletonClass(ClassBImpl.class)
            .addSingletonClass(ClassCImpl.class)
            .addSingletonClass(ClassDImpl.class)
            .addSingletonClass(ClassEImpl.class)
            .addSingletonClass(ClassFImpl.class)
            .addSingletonClass(ClassGImpl.class)
            .addSingletonClass(ClassHImpl.class)
            .errorHandler(error -> {
                throw new RuntimeException(error);
            })
            .build();
    }
}
