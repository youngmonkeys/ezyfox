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
		byte[] bytes = messageSerializer.serialize(data);
		return bytes;
	}
	
	protected abstract Object marshalEntity(Object entity);

	@Override
	public <T> T deserialize(byte[] data, Class<T> entityType) {
		Object value = messageDeserializer.deserialize(data);
		T entity = unmarshalValue(value, entityType);
		return entity;
	}
	
	protected abstract <T> T unmarshalValue(Object value, Class<T> entityType);
	
	@SuppressWarnings("unchecked")
	public static abstract class Builder<B extends Builder<B>> 
			implements EzyBuilder<EzyEntityCodec> {
		
		protected EzyMessageSerializer messageSerializer;
		protected EzyMessageDeserializer messageDeserializer;
		
		public B messageSerializer(EzyMessageSerializer messageSerializer) {
			this.messageSerializer = messageSerializer;
			return (B)this;
		}
		
		public B messageDeserializer(EzyMessageDeserializer messageDeserializer) {
			this.messageDeserializer = messageDeserializer;
			return (B)this;
		}
	}

}

