package com.tvd12.ezyfox.boot;

import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.EzyPackagesToScanAware;
import com.tvd12.ezyfox.bean.EzySingletonFactory;
import com.tvd12.ezyfox.bean.EzySingletonFactoryAware;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import lombok.Setter;

import java.util.Set;

@EzyConfigurationBefore
public class DatabaseConfiguration implements
    EzySingletonFactoryAware,
    EzyPackagesToScanAware,
    EzyBeanConfig {

    @Setter
    private Set<String> packagesToScan;

    @Setter
    private EzySingletonFactory singletonFactory;


    @Override
    public void config() {
        singletonFactory.addSingleton(new DatabaseContext(packagesToScan));
    }
}
