package com.tvd12.ezyfox.bean.v120.testing.packet03;

import com.tvd12.ezyfox.bean.EzyBeanAutoConfig;
import com.tvd12.ezyfox.bean.EzySingletonFactory;
import com.tvd12.ezyfox.bean.EzySingletonFactoryAware;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import lombok.Setter;

@EzyConfigurationBefore
public class Config32 implements EzySingletonFactoryAware, EzyBeanAutoConfig {

    @Setter
    private EzySingletonFactory singletonFactory;

    @Override
    public void autoConfig() {
        singletonFactory.addSingleton(new Singleton32());
    }
}
