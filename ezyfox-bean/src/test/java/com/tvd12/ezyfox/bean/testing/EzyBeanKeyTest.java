package com.tvd12.ezyfox.bean.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.impl.EzyBeanKey;
import com.tvd12.test.base.BaseTest;

public class EzyBeanKeyTest extends BaseTest {

    @Test
    public void test() {
        new EzyBeanKey("hello", getClass()).toString();
    }

}
