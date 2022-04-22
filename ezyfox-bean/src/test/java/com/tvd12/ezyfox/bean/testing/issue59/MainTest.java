package com.tvd12.ezyfox.bean.testing.issue59;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanContext;

public class MainTest {

    @Test
    public void test() {
        EzyBeanContext.builder()
            .scan("com.tvd12.ezyfox.bean.testing.issue59")
            .build();
    }
}