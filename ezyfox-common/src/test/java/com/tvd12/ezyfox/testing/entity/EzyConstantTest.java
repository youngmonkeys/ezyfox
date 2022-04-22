package com.tvd12.ezyfox.testing.entity;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.constant.EzyConstant;
import com.tvd12.test.base.BaseTest;

public class EzyConstantTest extends BaseTest {

    @Test
    public void test() {
        EzyConstant ct = EzyConstant.one();
        assert ct.getId() >= 0;
        assert ct.getName().length() >= 0;
    }
}