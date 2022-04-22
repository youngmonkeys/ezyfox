package com.tvd12.ezyfox.codec;

public interface EzyEntityDeserializer {

    <T> T deserialize(byte[] data, Class<T> entityType);
}