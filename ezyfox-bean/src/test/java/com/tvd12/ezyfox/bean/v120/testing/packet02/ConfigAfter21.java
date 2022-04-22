package com.tvd12.ezyfox.bean.v120.testing.packet02;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

public class ConfigAfter21 {

    @EzyAutoBind
    private Singleton21 singleton21;

    @EzySingleton
    public LastSingleton21 lastSingleton21() {
        return new LastSingleton21(singleton21);
    }}