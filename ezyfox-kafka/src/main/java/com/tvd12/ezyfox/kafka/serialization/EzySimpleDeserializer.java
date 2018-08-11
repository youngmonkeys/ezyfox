package com.tvd12.ezyfox.kafka.serialization;

import static com.tvd12.ezyfox.kafka.constant.EzySerializationConfig.MESSAGE_DESERIALIZER;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.tvd12.ezyfox.codec.EzyMessageDeserializer;
import com.tvd12.ezyfox.reflect.EzyClasses;

public class EzySimpleDeserializer implements Deserializer<Object> {

	protected EzyMessageDeserializer deserializer;
	
	@Override
	public void close() {
	}

	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		String deserializerImplClass = (String) configs.get(MESSAGE_DESERIALIZER);
		deserializer = EzyClasses.newInstance(deserializerImplClass); 
	}
	
	@Override
	public Object deserialize(String topic, byte[] data) {
		return deserializer.deserialize(data);
	}
	
	
}
