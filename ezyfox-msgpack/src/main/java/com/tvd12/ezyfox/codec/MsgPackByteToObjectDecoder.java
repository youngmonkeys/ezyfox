package com.tvd12.ezyfox.codec;

import java.nio.ByteBuffer;
import java.util.Queue;

public class MsgPackByteToObjectDecoder implements EzyByteToObjectDecoder {

	protected final EzyDefaultDecodeHandlers handlers;
	protected final EzyMessageDeserializer deserializer;
	
	public MsgPackByteToObjectDecoder(
			EzyMessageDeserializer deserializer, int maxSize) {
		this.deserializer = deserializer;
		this.handlers = EzyDefaultDecodeHandlers.builder()
				.maxSize(maxSize)
				.build();
	}
	
	@Override
	public Object decode(EzyMessage message) throws Exception {
		Object answer = deserializer.deserialize(message.getContent());
		return answer;
	}
	
	@Override
	public void decode(ByteBuffer bytes, Queue<EzyMessage> out) throws Exception {
		handlers.handle(bytes, out);
	}
	
}
