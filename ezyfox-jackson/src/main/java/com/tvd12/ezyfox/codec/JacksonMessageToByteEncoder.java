package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.codec.EzyMessageByTypeSerializer;
import com.tvd12.ezyfox.codec.EzyObjectToStringEncoder;

public class JacksonMessageToByteEncoder implements EzyObjectToStringEncoder {

	protected final EzyMessageByTypeSerializer serializer;
	
	public JacksonMessageToByteEncoder(EzyMessageByTypeSerializer serializer) {
		this.serializer = serializer;
	}
	
	@Override
	public byte[] encode(Object msg) throws Exception {
		byte[] bytes = serializer.serialize(msg);
		return bytes;
	}
	
	@Override
	public <T> T encode(Object msg, Class<T> outType) throws Exception {
		T answer = serializer.serialize(msg, outType);
		return answer;
	}
	
}
