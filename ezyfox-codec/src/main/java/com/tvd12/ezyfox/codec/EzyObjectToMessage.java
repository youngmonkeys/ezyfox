package com.tvd12.ezyfox.codec;

public interface EzyObjectToMessage {

	EzyMessage convert(Object object);
	
	default byte[] convertToMessageContent(Object object) {
		throw new UnsupportedOperationException("unsupported");
	}
	
	default EzyMessage packToMessage(byte[] content, boolean encrypted) {
		throw new UnsupportedOperationException("unsupported");
	}
}
