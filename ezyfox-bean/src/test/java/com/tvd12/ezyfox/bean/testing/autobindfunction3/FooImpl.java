package com.tvd12.ezyfox.bean.testing.autobindfunction3;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EzySingleton("foo")
public class FooImpl implements Foo {

    @EzyAutoBind
    private Bar bar;
}
