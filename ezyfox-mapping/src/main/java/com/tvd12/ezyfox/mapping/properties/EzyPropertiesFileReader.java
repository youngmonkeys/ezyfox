package com.tvd12.ezyfox.mapping.properties;

public interface EzyPropertiesFileReader {

    <T> T read(String filePath, Class<T> valueType);
}
