package com.tvd12.ezyfox.bean.testing.combine3.pack1;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzySingleton
public class AImpl implements A {

    @EzyAutoBind
    public AImpl(B b, C c, D d, E e, F f) {
    }
}
