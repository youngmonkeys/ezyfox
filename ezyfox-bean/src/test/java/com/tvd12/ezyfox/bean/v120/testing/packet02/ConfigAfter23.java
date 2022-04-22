package com.tvd12.ezyfox.bean.v120.testing.packet02;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

public class ConfigAfter23 {

    @EzyAutoBind
    private Singleton21 singleton21;

    @EzySingleton
    public LastSingleton23 lastSingleton23() {
        return new LastSingleton23(singleton21);
    }
}
