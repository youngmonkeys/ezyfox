package com.tvd12.ezyfox.bean.testing.combine.pack3;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzySingleton
public class Pack3A2Impl implements Pack3A2 {

    @EzyAutoBind
    public Pack3A3 pack3A3x;
}
