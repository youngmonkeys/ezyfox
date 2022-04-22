package com.tvd12.ezyfox.bean.testing.autobindfunction1;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.testing.autobindfunction2.Hello;

public abstract class World0 extends World {

    @EzyAutoBind
    @Override
    public void setHello(Hello hello) {
        super.setHello(hello);
    }
}