package com.tvd12.ezyfox.bean.testing.combine2;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfox.bean.impl.EzyByConstructorPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.impl.EzySimpleErrorHandler;
import org.testng.annotations.Test;

public class Combine2Test {

    @Test
    public void test() throws Exception {
        EzyByConstructorPrototypeSupplierLoader.setDebug(true);
        EzyBeanContextBuilder builder = EzyBeanContext.builder()
            .scan(
                "com.tvd12.ezyfox.bean.testing.combine2"
            )
            .errorHandler(new EzySimpleErrorHandler());
        EzyBeanContext context = builder.build();
        context.getPrototypeFactory();
    }
}
