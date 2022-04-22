package com.tvd12.ezyfox.bean.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanAutoConfig;

public class EzyBeanAutoConfigTest {

    @Test
    public void testSuccess() {
        new A().config();
    }

    @Test
    public void testException() {
        new B().config();
    }

    private static class A implements EzyBeanAutoConfig {
        @Override
        public void autoConfig() {
        }
    }

    private static class B implements EzyBeanAutoConfig {
        @Override
        public void autoConfig() {
            throw new IllegalStateException();
        }
    }

}
