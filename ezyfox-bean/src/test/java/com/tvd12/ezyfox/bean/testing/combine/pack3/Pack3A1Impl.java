package com.tvd12.ezyfox.bean.testing.combine.pack3;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzySingleton
public class Pack3A1Impl implements Pack3A1 {

    @EzyAutoBind
    public Pack3A2 pack3A2;
}