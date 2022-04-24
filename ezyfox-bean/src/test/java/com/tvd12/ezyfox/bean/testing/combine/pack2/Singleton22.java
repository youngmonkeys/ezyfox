package com.tvd12.ezyfox.bean.testing.combine.pack2;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@EzySingleton("s22")
public class Singleton22 {

    @Setter
    @EzyAutoBind
    private ArrayList<String> list;

    @EzyAutoBind({"singleton21", "abc"})
    public Singleton22(ISingleton21 singleton21) {}
}
