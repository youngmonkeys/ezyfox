package com.tvd12.ezyfox.binding.testing.testing2;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;

import lombok.Getter;
import lombok.Setter;

@EzyObjectBinding(subTypes = true, subTypeClasses = {InterfaceB.class})
public class ClassB implements InterfaceB {

    @Setter
    @Getter
    private String name;

}
