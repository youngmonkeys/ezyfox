package com.tvd12.ezyfox.bean.testing.singleton;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.properties.file.annotation.Property;
import lombok.Getter;
import lombok.Setter;

@Getter
@EzySingleton("a")
public class ClassA {

    @Setter
    @Property("hello_world")
    private String hello;

    private final String value1 = "1";
}
