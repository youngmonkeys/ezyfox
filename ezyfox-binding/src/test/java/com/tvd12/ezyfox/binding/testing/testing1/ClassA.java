package com.tvd12.ezyfox.binding.testing.testing1;

import com.tvd12.ezyfox.binding.annotation.EzyArrayBinding;
import lombok.Getter;
import lombok.Setter;

@EzyArrayBinding(subTypes = true)
public class ClassA implements InterfaceA {

    @Getter
    @Setter
    private String name;
}
