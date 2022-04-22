package com.tvd12.ezyfox.bean.testing.complex;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;

public class ClassBImpl implements ClassB {

    @EzyAutoBind
    public ClassBImpl(ClassC classC) {
    }
}