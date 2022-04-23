package com.tvd12.ezyfox.binding.codec.test;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.codec.EzyBindingEntityCodec;
import com.tvd12.ezyfox.codec.EzyEntityCodec;
import com.tvd12.ezyfox.codec.MsgPackSimpleDeserializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleSerializer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class EzyBindingEntityCodecTest {

    @Test
    public void test() {
        EzyBindingContext bindingContext = EzyBindingContext.builder()
                .addClass(A.class)
                .build();
        EzyEntityCodec codec = EzyBindingEntityCodec.builder()
                .marshaller(bindingContext.newMarshaller())
                .unmarshaller(bindingContext.newUnmarshaller())
                .messageSerializer(new MsgPackSimpleSerializer())
                .messageDeserializer(new MsgPackSimpleDeserializer())
                .build();
        byte[] bytes = codec.serialize(new A("hello"));
        A a = codec.deserialize(bytes, A.class);
        assert a.getValue().equals("hello");
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class A {
        protected String value;
    }
}
