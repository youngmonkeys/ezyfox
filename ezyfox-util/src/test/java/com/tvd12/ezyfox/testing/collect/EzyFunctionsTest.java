package com.tvd12.ezyfox.testing.collect;

import com.tvd12.ezyfox.function.EzyFunctions;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

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
