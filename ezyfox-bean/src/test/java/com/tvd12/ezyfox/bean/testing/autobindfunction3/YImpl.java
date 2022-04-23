package com.tvd12.ezyfox.bean.testing.autobindfunction3;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EzySingleton("y")
public class YImpl implements Y {

    @EzyAutoBind
    private Z z;
}
