package com.tvd12.ezyfox.bean.testing.complex;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;

public class ClassDImpl implements ClassD {

    @EzyAutoBind
    public ClassDImpl(ClassE classE) {
    }
}