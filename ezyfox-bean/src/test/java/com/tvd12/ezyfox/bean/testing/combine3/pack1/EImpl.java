package com.tvd12.ezyfox.bean.testing.combine3.pack1;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzySingleton
public class EImpl implements E {

    @EzyAutoBind
    public EImpl(B b, C c, D d, F f) {
        System.out.println("EImpl: b = " + b + ", c = " + c + ", d = " + d + ", f = " + f);
    }

}
