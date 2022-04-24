package com.tvd12.ezyfox.bean.testing.combine.pack2;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@EzySingleton("s2")
public class Singleton2 {

    @Setter
    @EzyAutoBind
    private ArrayList<String> list;

    @Setter
    @EzyAutoBind
    private ISingleton10 sgt10;
}
