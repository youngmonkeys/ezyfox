package com.tvd12.ezyfox.bean.testing.singleton;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.Getter;

@Getter
@EzySingleton
public class ClassC {

    private final String value3 = "3";
}
