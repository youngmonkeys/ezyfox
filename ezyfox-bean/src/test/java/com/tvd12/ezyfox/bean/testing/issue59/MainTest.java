package com.tvd12.ezyfox.bean.testing.issue59;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import org.testng.annotations.Test;

public class MainTest {

    @Test
    public void test() {
        EzyBeanContext.builder()
            .scan("com.tvd12.ezyfox.bean.testing.issue59")
            .build();
    }
}
