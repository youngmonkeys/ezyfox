package com.tvd12.ezyfox.bean.testing.combine.pack2;

import java.util.ArrayList;

import com.tvd12.ezyfox.annotation.EzyProperty;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

import lombok.Getter;

@EzySingleton("s20")
public class Singleton20 {

    @Getter
    @EzyProperty("hello")
    private String hello;

    @Getter
    @EzyAutoBind
    private ArrayList<String> list;

    @Getter
    @EzyAutoBind
    private ISingleton10 sgt10;

    @EzyAutoBind
    protected void setStg21(ISingleton21 stg21) {
        System.out.println("set stg21: " + stg21);
    }
}
