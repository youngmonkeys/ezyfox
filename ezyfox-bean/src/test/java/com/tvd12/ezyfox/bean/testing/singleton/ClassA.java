package com.tvd12.ezyfox.bean.testing.singleton;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.Getter;

@Getter
@EzySingleton("a")
public class ClassA {

    private final String value1 = "1";
}
