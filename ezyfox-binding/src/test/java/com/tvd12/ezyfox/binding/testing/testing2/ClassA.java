package com.tvd12.ezyfox.binding.testing.testing2;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;

import lombok.Getter;
import lombok.Setter;

@EzyObjectBinding(subTypes = true)
public class ClassA implements InterfaceA {

    @Getter
    @Setter
    private String name;
}