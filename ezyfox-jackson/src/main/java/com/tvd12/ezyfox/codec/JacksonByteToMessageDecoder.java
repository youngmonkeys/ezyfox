package com.tvd12.ezyfox.codec;

import java.nio.ByteBuffer;

import com.tvd12.ezyfox.codec.EzyMessageDeserializer;
import com.tvd12.ezyfox.codec.EzyStringToObjectDecoder;

public class JacksonByteToMessageDecoder implements EzyStringToObjectDecoder {

	private final EzyMessageDeserializer deserializer;
	
	public JacksonByteToMessageDecoder(EzyMessageDeserializer deserializer) {
		this.deserializer = deserializer;
	}
	
	@Override
	public Object decode(String bytes) throws Exception {
		Object answer = deserializer.deserialize(bytes);
		return answer;
	}
	
	@Override
	public Object decode(ByteBuffer bytes) throws Exception {
		Object answer = deserializer.deserialize(bytes);
		return answer;
	}
	
}
