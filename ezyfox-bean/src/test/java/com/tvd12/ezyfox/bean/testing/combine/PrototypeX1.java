package com.tvd12.ezyfox.bean.testing.combine;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PrototypeX1 {

    private final SingletonX1 singletonX1;
    private final SingletonX2 singletonX2;
    private final SingletonX3 singletonX3;
}
