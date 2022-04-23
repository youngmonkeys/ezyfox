package com.tvd12.ezyfox.testing.codec;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.EzyBytesEntityCodec;
import com.tvd12.ezyfox.codec.EzyEntityCodec;
import com.tvd12.ezyfox.codec.EzyMessageDeserializer;
import com.tvd12.ezyfox.codec.EzyMessageSerializer;
import com.tvd12.test.assertion.Asserts;

import static org.mockito.Mockito.*;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class EzyBytesEntityCodecTest {

    @Test
    public void test() {
        EzyMessageSerializer messageSerializer = mock(EzyMessageSerializer.class);
        when(messageSerializer.serialize(any())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0].toString().getBytes();
            }
        });
        EzyMessageDeserializer messageDeserializer = mock(EzyMessageDeserializer.class);
        when(messageDeserializer.deserialize(any(byte[].class))).thenReturn("abc");
        EzyEntityCodec codec = ExEzyBytesEntityCodec.builder()
                .messageSerializer(messageSerializer)
                .messageDeserializer(messageDeserializer)
                .build();
        String hello = "hello";
        assert new String(codec.serialize(hello)).equals(hello);
        assert codec.deserialize("abc".getBytes(), String.class).equals("abc");

        Asserts.assertNull(codec.deserialize(null, Object.class));
    }

    @Test
    public void serializeNull() {
        // given
        EzyMessageSerializer messageSerializer = mock(EzyMessageSerializer.class);
        when(messageSerializer.serialize(null)).thenReturn(new byte[0]);

        EzyEntityCodec codec = ExEzyBytesEntityCodec.builder()
                .messageSerializer(messageSerializer)
                .build();

        // when
        byte[] actual = codec.serialize(null);

        // then
        Asserts.assertEquals(new byte[0], actual);
    }

    public static class ExEzyBytesEntityCodec extends EzyBytesEntityCodec {

        protected ExEzyBytesEntityCodec(Builder builder) {
            super(builder);
        }

        @Override
        protected Object marshalEntity(Object entity) {
            return entity;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected <T> T unmarshalValue(Object value, Class<T> entityType) {
            return (T)value;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder extends EzyBytesEntityCodec.Builder<Builder> {

            @Override
            public EzyEntityCodec build() {
                return new ExEzyBytesEntityCodec(this);
            }

        }

    }
}
