package com.tvd12.ezyfox.binding.testing.arraybinding;

import com.tvd12.ezyfox.collect.Lists;

import java.util.List;

public class ClassA5 {

    public List<?> getA() {
        return Lists.newArrayList(1, 2, 3);
    }

    public void setA(List<?> a) {}
}
