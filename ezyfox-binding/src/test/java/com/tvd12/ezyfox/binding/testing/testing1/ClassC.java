package com.tvd12.ezyfox.binding.testing.testing1;

import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;

import lombok.Getter;
import lombok.Setter;

@EzyArrayBinding
public class ClassC implements InterfaceC {

    @Setter
    @Getter
    private String name;
}