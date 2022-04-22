package com.tvd12.ezyfox.codec;

public class JacksonByteToMessageDecoder implements EzyStringToObjectDecoder {

    private final EzyMessageDeserializer deserializer;

    public JacksonByteToMessageDecoder(EzyMessageDeserializer deserializer) {
        this.deserializer = deserializer;
    }

    @Override
    public Object decode(String bytes) throws Exception {
        Object answer = deserializer.deserialize(bytes);
        return answer;
    }

    @Override
    public Object decode(byte[] bytes) throws Exception {
        Object answer = deserializer.deserialize(bytes);
        return answer;
    }
}