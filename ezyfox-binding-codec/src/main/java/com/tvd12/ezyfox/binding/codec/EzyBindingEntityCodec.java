package com.tvd12.ezyfox.binding.codec;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.codec.EzyBytesEntityCodec;
import com.tvd12.ezyfox.codec.EzyEntityCodec;

public class EzyBindingEntityCodec extends EzyBytesEntityCodec {

    protected final EzyMarshaller marshaller;
    protected final EzyUnmarshaller unmarshaller;

    protected EzyBindingEntityCodec(Builder builder) {
        super(builder);
        this.marshaller = builder.marshaller;
        this.unmarshaller = builder.unmarshaller;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    protected Object marshalEntity(Object entity) {
        return marshaller.marshal(entity);
    }

    @Override
    protected <T> T unmarshalValue(Object value, Class<T> entityType) {
        return unmarshaller.unmarshal(value, entityType);
    }

    public static class Builder extends EzyBytesEntityCodec.Builder<Builder> {

        protected EzyMarshaller marshaller;
        protected EzyUnmarshaller unmarshaller;

        public Builder marshaller(EzyMarshaller marshaller) {
            this.marshaller = marshaller;
            return this;
        }

        public Builder unmarshaller(EzyUnmarshaller unmarshaller) {
            this.unmarshaller = unmarshaller;
            return this;
        }

        @Override
        public EzyEntityCodec build() {
            return new EzyBindingEntityCodec(this);
        }
    }
}
