package com.tvd12.ezyfox.bean.testing.singleton1;

import com.tvd12.ezyfox.annotation.EzyKeyValue;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

@EzySingleton(properties = {
        @EzyKeyValue(key = "type", value = "request_listener"),
        @EzyKeyValue(key = "cmd", value = "2"),
        @EzyKeyValue(key = "priority", value = "1")
})
public class ClassB {
}
