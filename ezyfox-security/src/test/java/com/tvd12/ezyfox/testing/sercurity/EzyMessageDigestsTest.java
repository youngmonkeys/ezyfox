package com.tvd12.ezyfox.testing.sercurity;

import com.tvd12.ezyfox.sercurity.EzyMessageDigests;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyMessageDigestsTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyMessageDigests.class;
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test() {
        EzyMessageDigests.getAlgorithm("i don't know");
    }
}
