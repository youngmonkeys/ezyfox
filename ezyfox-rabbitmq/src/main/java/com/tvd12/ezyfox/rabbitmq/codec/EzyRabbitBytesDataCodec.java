package com.tvd12.ezyfox.rabbitmq.codec;

import java.util.Map;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.codec.EzyMessageDeserializer;
import com.tvd12.ezyfox.codec.EzyMessageSerializer;

import lombok.Setter;

@Setter
@SuppressWarnings("rawtypes")
public class EzyRabbitBytesDataCodec extends EzyAbstractRabbitDataCodec  {

	protected EzyMessageSerializer messageSerializer;
	protected EzyMessageDeserializer messageDeserializer;
	
	public EzyRabbitBytesDataCodec() {
	}
	
	public EzyRabbitBytesDataCodec(
			EzyMessageSerializer messageSerializer,
			EzyMessageDeserializer messageDeserializer) {
		this.messageSerializer = messageSerializer;
		this.messageDeserializer = messageDeserializer;
	}
	
	public EzyRabbitBytesDataCodec(
			EzyMarshaller marshaller, 
			EzyUnmarshaller unmarshaller,
			EzyMessageSerializer messageSerializer,
			EzyMessageDeserializer messageDeserializer,
	        Map<String, Class> requestTypeMap, 
	        Map<String, Class> responseTypeMap) {
		super(marshaller, unmarshaller, requestTypeMap, responseTypeMap);
		this.messageSerializer = messageSerializer;
		this.messageDeserializer = messageDeserializer;
	}
	
	@Override
	public Object deserialize(String cmd, byte[] request) {
		Object data = messageDeserializer.deserialize(request);
		Object entity = unmarshallData(cmd, data);
		return entity;
	}

	@Override
	public byte[] serialize(String cmd, Object response) {
		Object data = marshallEntity(cmd, response);
		byte[] bytes = messageSerializer.serialize(data);
		return bytes;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder extends EzyAbstractRabbitDataCodecBuilder<Builder> {
		protected EzyMessageSerializer messageSerializer;
		protected EzyMessageDeserializer messageDeserializer;
		
		public Builder messageSerializer(EzyMessageSerializer messageSerializer) {
			this.messageSerializer = messageSerializer;
			return this;
		}
		public Builder messageDeserializer(EzyMessageDeserializer messageDeserializer) {
			this.messageDeserializer = messageDeserializer;
			return this;
		}
		
		@Override
		protected EzyAbstractRabbitDataCodec newProduct() {
			return new EzyRabbitBytesDataCodec(messageSerializer, messageDeserializer);
		}
	}

}
