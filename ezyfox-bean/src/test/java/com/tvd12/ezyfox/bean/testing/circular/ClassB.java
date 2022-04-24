package com.tvd12.ezyfox.bean.testing.circular;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzySingleton
public class ClassB {

    @EzyAutoBind
    public ClassB(ClassA classA) {}
}
