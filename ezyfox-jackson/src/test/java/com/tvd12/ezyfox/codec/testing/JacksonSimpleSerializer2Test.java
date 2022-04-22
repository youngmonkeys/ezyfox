package com.tvd12.ezyfox.codec.testing;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.codec.EzyMessageByTypeSerializer;
import com.tvd12.ezyfox.codec.EzyMessageDeserializer;
import com.tvd12.ezyfox.codec.JacksonSimpleDeserializer;
import com.tvd12.ezyfox.codec.JacksonSimpleSerializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.jackson.JacksonObjectMapperBuilder;
import com.tvd12.test.base.BaseTest;
import com.tvd12.test.performance.Performance;

public class JacksonSimpleSerializer2Test extends BaseTest {

    private ObjectMapper objectMapper = newObjectMapper();
    private EzyMessageByTypeSerializer serializer 
                = new JacksonSimpleSerializer(objectMapper);
    private EzyMessageDeserializer deserializer
                = new JacksonSimpleDeserializer(objectMapper);
    
    @Test
    public void test() throws JsonProcessingException {
        EzyArray origin = EzyEntityFactory.create(EzyArrayBuilder.class)
                .append("a", 1)
                .append("b", 2)
                .append("c", 3)
                .append("d", new BigInteger("123"))
                .append("e", new BigDecimal("45.6"))
                .append("f", UUID.randomUUID())
                .append(EzyEntityFactory.create(EzyArrayBuilder.class))
                .build();
        byte[] bytes = serializer.serialize(origin);
        EzyArray array = deserializer.deserialize(bytes);
        System.out.println(array);
    }
    
    @SuppressWarnings({ "unused" })
    @Test
    public void test2() {
        EzyArray origin = EzyEntityFactory.create(EzyArrayBuilder.class)
                .append("a", 1)
                .append("b", 2)
                .append("c", 3)
                .append(EzyEntityFactory.create(EzyArrayBuilder.class))
                .build();
        long time = Performance.create()
            .loop(100000)
            .test(() -> {
                byte[] bytes = serializer.serialize(origin);
                String text = serializer.serialize(origin, String.class);
                ByteBuffer buffer = serializer.serialize(origin, ByteBuffer.class);
            })
            .getTime();
        System.out.println("test2 time = : " + time);
    }
    
    private ObjectMapper newObjectMapper() {
        return JacksonObjectMapperBuilder.newInstance().build();
    }
}
