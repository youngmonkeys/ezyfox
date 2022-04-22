package com.tvd12.ezyfox.bean.v120.testing.packet03;

import com.tvd12.ezyfox.bean.EzyBeanAutoConfig;
import com.tvd12.ezyfox.bean.annotation.EzyExclusiveClassesConfiguration;

@EzyExclusiveClassesConfiguration({Config32.class, Config32.class})
public class Config33 implements EzyBeanAutoConfig {

    @Override
    public void config() {
        throw new RuntimeException("just test");
    }

    @Override
    public void autoConfig() {
    }

}
