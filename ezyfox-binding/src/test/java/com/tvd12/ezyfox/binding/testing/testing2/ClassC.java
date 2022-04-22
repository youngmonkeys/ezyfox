package com.tvd12.ezyfox.binding.testing.testing2;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;

import lombok.Getter;
import lombok.Setter;

@EzyObjectBinding
public class ClassC implements InterfaceC {

    @Setter
    @Getter
    private String name;
}