package com.tvd12.ezyfox.binding.testing.arraybinding;

import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;
import com.tvd12.ezyfox.collect.Lists;

import java.util.List;

@EzyArrayBinding(accessType = EzyAccessType.DECLARED_ELEMENTS)
public class ClassA7 extends ClassA6 {

    public String a63 = "a63";
    protected String a61 = "a61";
    protected String a62 = "a62";
    protected List<?> a64 = Lists.newArrayList(1, 2, 3);

    public String getA61() {
        return a61;
    }

    public void setA61(String a61) {
        this.a61 = a61;
    }

    public List<?> getA64() {
        return a64;
    }

    public void setA64(List<?> a64) {
        this.a64 = a64;
    }
}
