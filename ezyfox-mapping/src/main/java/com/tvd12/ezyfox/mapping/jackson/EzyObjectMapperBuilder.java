package com.tvd12.ezyfox.mapping.jackson;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.jackson.JacksonArraySerializer;
import com.tvd12.ezyfox.jackson.JacksonObjectSerializer;

public class EzyObjectMapperBuilder implements EzyBuilder<ObjectMapper> {

    public static EzyObjectMapperBuilder objectMapperBuilder() {
        return new EzyObjectMapperBuilder();
    }

    @Override
    public ObjectMapper build() {
        return new ObjectMapper()
                .registerModule(newModule());
    }

    protected Module newModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(new JacksonArraySerializer());
        module.addSerializer(new JacksonObjectSerializer());
        return module;
    }
}
