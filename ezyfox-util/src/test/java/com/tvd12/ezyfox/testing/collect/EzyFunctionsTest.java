package com.tvd12.ezyfox.testing.collect;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.function.EzyFunctions;
import com.tvd12.test.base.BaseTest;

public class EzyFunctionsTest extends BaseTest {

    @Test
    public void test() {
        EzyFunctions.newReentrantLockFunc().apply(new Object());
    }

    @Override
    public Class<?> getTestClass() {
        return EzyFunctions.class;
    }
}
