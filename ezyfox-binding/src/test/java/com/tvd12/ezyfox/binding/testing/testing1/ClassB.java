package com.tvd12.ezyfox.binding.testing.testing1;

import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;

import lombok.Getter;
import lombok.Setter;

@EzyArrayBinding(subTypes = true, subTypeClasses = {InterfaceB.class})
public class ClassB implements InterfaceB {

    @Setter
    @Getter
    private String name;
}