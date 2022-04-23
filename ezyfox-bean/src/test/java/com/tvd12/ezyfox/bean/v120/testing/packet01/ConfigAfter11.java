package com.tvd12.ezyfox.bean.v120.testing.packet01;

import com.tvd12.ezyfox.bean.annotation.EzyConfigurationAfter;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzyConfigurationAfter
public class ConfigAfter11 {

    @EzySingleton
    public LastSingleton11 lastSingleton11(Singleton11 singleton11) {
        return new LastSingleton11(singleton11);
    }
}
