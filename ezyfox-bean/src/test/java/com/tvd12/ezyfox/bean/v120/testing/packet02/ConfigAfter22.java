package com.tvd12.ezyfox.bean.v120.testing.packet02;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

public class ConfigAfter22 {

    @EzyAutoBind
    private Singleton21 singleton21;

    @EzySingleton
    public LastSingleton22 lastSingleton22() {
        return new LastSingleton22(singleton21);
    }}