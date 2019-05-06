package com.tvd12.ezyfox.kafka.codec;

import java.util.Map;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.codec.EzyMessageDeserializer;
import com.tvd12.ezyfox.codec.EzyMessageSerializer;

import lombok.Setter;

@Setter
@SuppressWarnings("rawtypes")
public class EzyKafkaBytesDataCodec extends EzyAbstractKafkaDataCodec  {

	protected EzyMessageSerializer messageSerializer;
	protected EzyMessageDeserializer messageDeserializer;
	
	public EzyKafkaBytesDataCodec() {
	}
	
	public EzyKafkaBytesDataCodec(
			EzyMessageSerializer messageSerializer,
			EzyMessageDeserializer messageDeserializer) {
		this.messageSerializer = messageSerializer;
		this.messageDeserializer = messageDeserializer;
	}
	
	public EzyKafkaBytesDataCodec(
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
	public byte[] serialize(Object response) {
		Object data = marshallEntity(response);
		byte[] bytes = messageSerializer.serialize(data);
		return bytes;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder extends EzyAbstractKafkaDataCodecBuilder<Builder> {
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
		protected EzyAbstractKafkaDataCodec newProduct() {
			return new EzyKafkaBytesDataCodec(messageSerializer, messageDeserializer);
		}
	}

}
