package com.tvd12.ezyfox.testing.entity;

import com.tvd12.ezyfox.constant.EzyConstant;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyConstantTest extends BaseTest {

    @SuppressWarnings("ConstantConditions")
    @Test
    public void test() {
        EzyConstant ct = EzyConstant.one();
        assert ct.getId() >= 0;
        assert ct.getName().length() >= 0;
    }
}
