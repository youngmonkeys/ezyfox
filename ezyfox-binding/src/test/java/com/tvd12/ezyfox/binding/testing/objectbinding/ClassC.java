package com.tvd12.ezyfox.binding.testing.objectbinding;

import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@EzyObjectBinding(accessType = EzyAccessType.DECLARED_ELEMENTS)
public class ClassC extends ClassA {

    private String a1 = "1";
    private String b1 = "2";
    private String c1 = "3";
}
