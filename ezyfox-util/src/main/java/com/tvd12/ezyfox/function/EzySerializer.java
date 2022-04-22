package com.tvd12.ezyfox.function;

public interface EzySerializer<I,O> {

    O serialize(I input);
    }