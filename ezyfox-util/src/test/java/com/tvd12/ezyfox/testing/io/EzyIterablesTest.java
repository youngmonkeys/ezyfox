package com.tvd12.ezyfox.testing.io;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.io.EzyIterables;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyIterablesTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyIterables.class;
    }

    @Test
    public void test() {
        assert EzyIterables.isEmpty(null);
        assert EzyIterables.isEmpty(Lists.newArrayList());
        assert !EzyIterables.isEmpty(Lists.newArrayList(1));
    }
}
