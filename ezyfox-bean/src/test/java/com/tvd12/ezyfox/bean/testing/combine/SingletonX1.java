package com.tvd12.ezyfox.bean.testing.combine;

import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SingletonX1 {

    @EzyAutoBind("")
    private SingletonX1 singletonX1;

    @EzyAutoBind
    private SingletonX2 singletonX2;
}