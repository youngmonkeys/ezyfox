package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.function.EzyParser;
import com.tvd12.ezyfox.io.EzyMaps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("rawtypes")
public abstract class EzyAbstractByTypeSerializer
    implements EzyMessageByTypeSerializer {

    protected Map<Class<?>, Map<Class<?>, EzyParser>> parserMaps
        = defaultParserMaps();

    @Override
    public <T> T serialize(Object value, Class<T> outType) {
        return value == null
            ? parseNil(outType)
            : parseNotNull(value, outType);
    }

    protected <T> T parseNotNull(Object value, Class<T> outType) {
        Map<Class<?>, EzyParser<Object, Object>> parsers
            = getParserMap(value.getClass());
        if (parsers == null) {
            return parseWithNoParsers(value, outType);
        }
        EzyParser<Object, Object> parser = parsers.get(outType);
        if (parser == null) {
            return parseWithNoParser(value, outType);
        }
        return parseWithParser(parser, value);
    }

    @SuppressWarnings("unchecked")
    protected <T> T parseWithParser(EzyParser<Object, Object> parser, Object value) {
        return (T) parser.parse(value);
    }

    protected <T> T parseWithNoParsers(Object value, Class<T> outType) {
        throw new IllegalArgumentException(
            "has no parse for " + value.getClass()
        );
    }

    protected <T> T parseWithNoParser(Object value, Class<T> outType) {
        throw new IllegalArgumentException(
            "has no parse for " + value.getClass() + " and outType " + outType
        );
    }

    protected abstract <T> T parseNil(Class<T> outType);

    protected Map<Class<?>, EzyParser<Object, Object>> getParserMap(
        Class<?> type
    ) {
        return EzyMaps.getValue(parserMaps, type);
    }

    protected Map<Class<?>, Map<Class<?>, EzyParser>> defaultParserMaps() {
        Map<Class<?>, Map<Class<?>, EzyParser>> map = new ConcurrentHashMap<>();
        addParserMap(map);
        return map;
    }

    protected abstract void addParserMap(
        Map<Class<?>, Map<Class<?>, EzyParser>> map
    );
}
