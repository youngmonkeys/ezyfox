package com.tvd12.ezyfox.codec;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.exception.EzyCodecException;
import com.tvd12.ezyfox.function.EzyParser;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JacksonSimpleSerializer
    extends EzyAbstractByTypeSerializer {

    protected final ObjectMapper objectMapper;

    public JacksonSimpleSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected <T> T parseNil(Class<T> outType) {
        return (T) writeValueAsBytes(null);
    }

    @Override
    protected <T> T parseWithNoParsers(Object value, Class<T> outType) {
        return (T) writeValueAsBytes(value);
    }

    @Override
    protected <T> T parseWithNoParser(Object value, Class<T> outType) {
        return (T) writeValueAsBytes(value);
    }

    protected byte[] writeValueAsBytes(Object obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            throw new EzyCodecException("write value as bytes error", e);
        }
    }

    protected String writeValueAsString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new EzyCodecException("write value as bytes error", e);
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected void addParserMap(Map<Class<?>, Map<Class<?>, EzyParser>> parserMaps) {
        Map<Class<?>, EzyParser> objectParsers = new ConcurrentHashMap<>();
        objectParsers.put(byte[].class, (EzyParser<EzyObject, byte[]>) this::writeValueAsBytes);
        objectParsers.put(String.class, (EzyParser<EzyObject, String>) this::writeValueAsString);
        objectParsers.put(
            ByteBuffer.class,
            (EzyParser<EzyObject, ByteBuffer>) input -> ByteBuffer.wrap(writeValueAsBytes(input))
        );
        parserMaps.put(EzyObject.class, objectParsers);

        Map<Class<?>, EzyParser> arrayParsers = new ConcurrentHashMap<>();
        arrayParsers.put(byte[].class, (EzyParser<EzyArray, byte[]>) this::writeValueAsBytes);
        arrayParsers.put(String.class, (EzyParser<EzyArray, String>) this::writeValueAsString);
        arrayParsers.put(
            ByteBuffer.class,
            (EzyParser<EzyArray, ByteBuffer>) input -> ByteBuffer.wrap(writeValueAsBytes(input))
        );
        parserMaps.put(EzyArray.class, arrayParsers);
    }
}
