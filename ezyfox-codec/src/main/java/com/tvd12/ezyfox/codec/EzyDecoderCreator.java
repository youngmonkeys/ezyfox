package com.tvd12.ezyfox.codec;

public interface EzyDecoderCreator {

    Object newDecoder(int maxRequestSize);

}
