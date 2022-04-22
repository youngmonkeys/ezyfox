package com.tvd12.ezyfox.bean.testing.autobindfunction3;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EzySingleton("greet")
public class GreetImpl implements Greet {

    @EzyAutoBind
    private Foo foo;

    @EzyAutoBind
    private X x;

}
