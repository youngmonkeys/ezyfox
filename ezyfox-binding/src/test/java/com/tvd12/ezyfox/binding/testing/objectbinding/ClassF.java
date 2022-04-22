package com.tvd12.ezyfox.binding.testing.objectbinding;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyWriter;
import com.tvd12.ezyfox.binding.writer.EzyMapArrayWriter;
import com.tvd12.ezyfox.binding.writer.EzyMapObjectWriter;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EzyObjectBinding(accessType = EzyAccessType.DECLARED_METHODS)
public class ClassF extends ClassA {

    private String a1 = "1";
    private String b1 = "2";
    private String c1 = "3";

    @EzyWriter(EzyMapObjectWriter.class)
    private Map<Object, Object> map = defaultMap();

    @EzyWriter(EzyMapArrayWriter.class)
    private Map<Object, Object> map2 = defaultMap();

    public void setD1(String d1) {
    }

    public String getD1() {
        return "d1";
    }

    private Map<Object, Object> defaultMap() {
        Map<Object, Object> map = new HashMap<>();
        map.put(new ClassA(), new ClassB());
        map.put("hello", "world");
        map.put("null", null);
        return map;
    }
}