package com.tvd12.ezyfox.bean.testing.complex;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;

public class ClassAImpl implements ClassA {

    @EzyAutoBind
    public ClassAImpl(ClassB classB) {
    }
}
