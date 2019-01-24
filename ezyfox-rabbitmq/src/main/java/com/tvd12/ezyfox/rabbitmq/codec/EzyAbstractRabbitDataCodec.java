package com.tvd12.ezyfox.rabbitmq.codec;

import java.util.Map;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;

import lombok.Setter;

@Setter
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class EzyAbstractRabbitDataCodec implements EzyRabbitDataCodec {

	protected EzyMarshaller marshaller;
	protected EzyUnmarshaller unmarshaller;
	protected Map<String, Class> requestTypeMap;
	protected Map<String, Class> responseTypeMap;
	
	public EzyAbstractRabbitDataCodec() {
	}
	
	public EzyAbstractRabbitDataCodec(
			EzyMarshaller marshaller,
			EzyUnmarshaller unmarshaller,
			Map<String, Class> requestTypeMap,
			Map<String, Class> responseTypeMap) {
		this.marshaller = marshaller;
		this.unmarshaller = unmarshaller;
		this.requestTypeMap = requestTypeMap;
		this.responseTypeMap = responseTypeMap;
	}
	
	protected Object marshallEntity(String cmd, Object entity) {
		Class responseType = responseTypeMap.get(cmd);
		if(responseType == null)
			throw new IllegalArgumentException("has no response type with command: " + cmd);
		Object answer = marshaller.marshal(entity);
		return answer;
	}
	
	protected Object unmarshallData(String cmd, Object value) {
		Class requestType = requestTypeMap.get(cmd);
		if(requestType == null)
			throw new IllegalArgumentException("has no request type with command: " + cmd);
		Object answer = unmarshaller.unmarshal(value, requestType);
		return answer;
	}
	
}
