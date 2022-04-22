package com.tvd12.ezyfox.bean.testing.autobindfunction;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyfox.bean.testing.autobindfunction2.Hello;
import com.tvd12.ezyfox.bean.testing.autobindfunction3.Greet;

import lombok.Setter;

@Setter
@EzySingleton("world")
public class World2 extends World1 {

    @EzyAutoBind
    private Greet greet;

    @EzyAutoBind
    @Override
    public void setHello(Hello hello) {
        super.setHello(hello);
        System.out.println("set hello 2 = " + hello);
    }

    @Override
    protected String getName() {
        return "world";
    }
}
