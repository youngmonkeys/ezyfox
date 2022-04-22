package com.tvd12.ezyfox.bean.testing.combine;

import com.tvd12.ezyfox.bean.annotation.EzyPropertiesBean;
import com.tvd12.ezyfox.bean.annotation.EzyPropertiesBeans;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzyPropertiesBeans({
    @EzyPropertiesBean(prefix = "", value = PropertiesCombine.class)
})
public class V111Configuration01 {

    @EzySingleton
    public V111Singleton02 v111Singleton02() {
        return new V111Singleton02();
    }

    @EzyPrototype
    public V111Prototype01 v111Prototype01() {
        return new V111Prototype01();
    }
}