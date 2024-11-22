package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.util.EzyEquals;
import com.tvd12.ezyfox.util.EzyHashCodes;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class EzyBeanKey implements Serializable {
    private static final long serialVersionUID = -2376464316946102262L;

    protected String name;
    protected Class<?> type;

    public EzyBeanKey(String name, Class<?> type) {
        this.name = name;
        this.type = type;
    }

    public static EzyBeanKey of(String name, Class<?> type) {
        return new EzyBeanKey(name, type);
    }

    @Override
    public boolean equals(Object obj) {
        return new EzyEquals<EzyBeanKey>()
            .function(o -> o.name)
            .function(o -> o.type)
            .isEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return new EzyHashCodes()
            .append(name)
            .append(type)
            .toHashCode();
    }

    @Override
    public String toString() {
        return "(" + name + "," + type.getName() + ")";
    }
}
