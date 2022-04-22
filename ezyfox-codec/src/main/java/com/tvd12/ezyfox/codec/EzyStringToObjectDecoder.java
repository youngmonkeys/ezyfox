package com.tvd12.ezyfox.codec;

public interface EzyStringToObjectDecoder {

    Object decode(String bytes) throws Exception;

    Object decode(byte[] bytes) throws Exception;
}