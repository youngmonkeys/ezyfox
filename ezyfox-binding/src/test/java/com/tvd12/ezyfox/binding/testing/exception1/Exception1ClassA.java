package com.tvd12.ezyfox.binding.testing.exception1;

import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;

@EzyObjectBinding
public class Exception1ClassA {

    public String getValue() {
        throw new IllegalArgumentException();
    }
}
