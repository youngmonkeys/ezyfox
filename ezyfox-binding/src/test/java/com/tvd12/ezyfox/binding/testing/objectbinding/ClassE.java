package com.tvd12.ezyfox.binding.testing.objectbinding;

import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EzyObjectBinding(accessType = EzyAccessType.DECLARED_METHODS)
public class ClassE extends ClassA {

    private String a1 = "1";
    private String b1 = "2";
    private String c1 = "3";

    public String getD1() {
        return "d1";
    }

    public void setD1(String d1) {}
}
