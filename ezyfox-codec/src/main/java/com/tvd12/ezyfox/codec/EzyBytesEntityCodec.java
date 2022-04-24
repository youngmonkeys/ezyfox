package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.builder.EzyBuilder;

public abstract class EzyBytesEntityCodec implements EzyEntityCodec {

    protected final EzyMessageSerializer messageSerializer;
    protected final EzyMessageDeserializer messageDeserializer;

    protected EzyBytesEntityCodec(Builder<?> builder) {
        this.messageSerializer = builder.messageSerializer;
        this.messageDeserializer = builder.messageDeserializer;
    }

    @Override
    public byte[] serialize(Object entity) {
        Object data = marshalEntity(entity);
        return messageSerializer.serialize(data);
    }

    protected abstract Object marshalEntity(Object entity);

    @Override
    public <T> T deserialize(byte[] data, Class<T> entityType) {
        if (data == null) {
            return null;
        }
        Object value = messageDeserializer.deserialize(data);
        return unmarshalValue(value, entityType);
    }

    protected abstract <T> T unmarshalValue(Object value, Class<T> entityType);

    @SuppressWarnings("unchecked")
    public abstract static class Builder<B extends Builder<B>>
        implements EzyBuilder<EzyEntityCodec> {

        protected EzyMessageSerializer messageSerializer;
        protected EzyMessageDeserializer messageDeserializer;

        public B messageSerializer(EzyMessageSerializer messageSerializer) {
            this.messageSerializer = messageSerializer;
            return (B) this;
        }

        public B messageDeserializer(EzyMessageDeserializer messageDeserializer) {
            this.messageDeserializer = messageDeserializer;
            return (B) this;
        }
    }
}
