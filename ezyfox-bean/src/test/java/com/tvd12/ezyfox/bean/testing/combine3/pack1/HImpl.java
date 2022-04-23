package com.tvd12.ezyfox.bean.testing.combine3.pack1;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzySingleton
public class HImpl implements H {

    @EzyAutoBind
    public HImpl(A a, B b, C c, D d, E e, G g) {
    }
}
