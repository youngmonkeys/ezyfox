package com.tvd12.ezyfox.bean.testing.complex;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;

public class ClassCImpl implements ClassC {

    @EzyAutoBind
    public ClassCImpl(ClassD classD) {
    }
}