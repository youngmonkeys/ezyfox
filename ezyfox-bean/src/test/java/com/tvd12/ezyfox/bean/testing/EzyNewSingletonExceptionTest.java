package com.tvd12.ezyfox.bean.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.exception.EzyNewSingletonException;

public class EzyNewSingletonExceptionTest {

    @Test
    public void test() {
        new EzyNewSingletonException(Object.class, Object.class, "object").getSingletonClass();
    }

}
