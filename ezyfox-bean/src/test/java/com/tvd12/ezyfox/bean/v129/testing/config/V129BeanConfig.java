package com.tvd12.ezyfox.bean.v129.testing.config;

import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzyConfigurationBefore
public class V129BeanConfig {

    @EzySingleton
    public final String hello = "hello";

    @EzySingleton
    public final String world = "world";

    @EzySingleton
    public String foo() {
        return "foo";
    }

    @EzySingleton
    public String bar() {
        return "bar";
    }
}
