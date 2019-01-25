package com.tvd12.ezyfox.rabbitmq.codec;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.codec.EzyEntityCodec;
import com.tvd12.ezyfox.codec.EzyMessageDeserializer;
import com.tvd12.ezyfox.codec.EzyMessageSerializer;

public class EzyRabbitBytesEntityCodec implements EzyEntityCodec {

	protected final EzyMarshaller marshaller;
	protected final EzyUnmarshaller unmarshaller;
	protected final EzyMessageSerializer messageSerializer;
	protected final EzyMessageDeserializer messageDeserializer;
	
	public EzyRabbitBytesEntityCodec(
			EzyMarshaller marshaller,
			EzyUnmarshaller unmarshaller,
			EzyMessageSerializer messageSerializer,
			EzyMessageDeserializer messageDeserializer) {
		this.marshaller = marshaller;
		this.unmarshaller = unmarshaller;
		this.messageSerializer = messageSerializer;
		this.messageDeserializer = messageDeserializer;
	}
	
	@Override
	public byte[] serialize(Object entity) {
		Object data = marshaller.marshal(entity);
		byte[] bytes = messageSerializer.serialize(data);
		return bytes;
	}

	@Override
	public <T> T deserialize(byte[] data, Class<T> entityType) {
		Object value = messageDeserializer.deserialize(data);
		T entity = unmarshaller.unmarshal(value, entityType);
		return entity;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder implements EzyBuilder<EzyEntityCodec> {
		protected EzyMarshaller marshaller;
		protected EzyUnmarshaller unmarshaller;
		protected EzyMessageSerializer messageSerializer;
		protected EzyMessageDeserializer messageDeserializer;
		
		public Builder marshaller(EzyMarshaller marshaller) {
			this.marshaller = marshaller;
			return this;
		}
		
		public Builder unmarshaller(EzyUnmarshaller unmarshaller) {
			this.unmarshaller = unmarshaller;
			return this;
		}
		
		public Builder messageSerializer(EzyMessageSerializer messageSerializer) {
			this.messageSerializer = messageSerializer;
			return this;
		}
		
		public Builder messageDeserializer(EzyMessageDeserializer messageDeserializer) {
			this.messageDeserializer = messageDeserializer;
			return this;
		}
		
		@Override
		public EzyEntityCodec build() {
			return new EzyRabbitBytesEntityCodec(
					marshaller,
					unmarshaller,
					messageSerializer, messageDeserializer);
		}
	}

}
