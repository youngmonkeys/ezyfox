package com.tvd12.ezyfox.bean.testing.annotation;

import com.tvd12.ezyfox.annotation.EzyKeyValue;
import com.tvd12.ezyfox.bean.annotation.EzyPrototype;

@IncludeAnnotation(properties = {
    @EzyKeyValue(key = "hello", value = "world")
})
@EzyPrototype(value = "hello", properties = {
        @EzyKeyValue(key = "type", value = "request_listener"),
        @EzyKeyValue(key = "cmd", value = "1"),
        @EzyKeyValue(key = "priority", value = "1")
})
public class ClassA {
}