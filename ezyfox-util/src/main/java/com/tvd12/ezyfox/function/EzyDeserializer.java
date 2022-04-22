package com.tvd12.ezyfox.function;

public interface EzyDeserializer<I,O> {

    O deserialize(I input);

}
