package com.tvd12.ezyfox.bean.testing.combine;

import com.tvd12.ezyfox.annotation.EzyProperty;
import com.tvd12.ezyfox.bean.testing.combine.pack0.PropertiesPack1;
import lombok.Getter;

@Getter
public class PropertiesCombine {

    @EzyProperty("hello")
    private String hello;

    @EzyProperty(prefix = "pack1")
    private PropertiesPack1 pack1;
}
