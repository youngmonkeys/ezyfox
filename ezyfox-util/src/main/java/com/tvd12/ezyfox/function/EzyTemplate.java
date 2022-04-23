package com.tvd12.ezyfox.function;

public interface EzyTemplate<I, O> 
        extends EzySerializer<I, O>, EzyDeserializer<O, I> {
}
