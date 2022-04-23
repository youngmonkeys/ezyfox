package com.tvd12.ezyfox.testing.collect;

import com.tvd12.ezyfox.function.EzyPredicates;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyPredicatesTest extends BaseTest {

    @Test
    public void test() {
        EzyPredicates.alwaysTrue().test(null);
    }

    @Override
    public Class<?> getTestClass() {
        return EzyPredicates.class;
    }
}
