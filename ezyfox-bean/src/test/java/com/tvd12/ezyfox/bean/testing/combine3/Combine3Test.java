package com.tvd12.ezyfox.bean.testing.combine3;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfox.bean.impl.EzyByConstructorPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.impl.EzySimpleErrorHandler;
import com.tvd12.ezyfox.bean.testing.combine3.pack1.A;
import com.tvd12.ezyfox.bean.testing.combine3.pack1.H;
import org.testng.annotations.Test;

public class Combine3Test {

    @Test
    public void test() throws Exception {
        EzyByConstructorPrototypeSupplierLoader.setDebug(true);
        EzyBeanContextBuilder builder = EzyBeanContext.builder()
            .scan("com.tvd12.ezyfox.bean.testing.combine3")
            .errorHandler(new EzySimpleErrorHandler());
        EzyBeanContext context = builder.build();
        A a = context.getSingleton("a", A.class);
        H h = context.getSingleton("h", H.class);
        assert a != null;
        assert h != null;
    }
}
