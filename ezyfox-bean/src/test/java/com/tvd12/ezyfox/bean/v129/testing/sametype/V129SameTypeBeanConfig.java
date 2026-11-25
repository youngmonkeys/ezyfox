package com.tvd12.ezyfox.bean.v129.testing.sametype;

import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzyConfigurationBefore
public class V129SameTypeBeanConfig {

    @EzySingleton
    public V129Holder holder() {
        return new V129Holder();
    }

    @EzySingleton
    public V129Wrapper wrapper(V129Value value) {
        return new V129Wrapper(value);
    }

    @EzySingleton
    public V129Value first() {
        return new V129Value("first");
    }

    @EzySingleton
    public V129Value second() {
        return new V129Value("second");
    }
}
