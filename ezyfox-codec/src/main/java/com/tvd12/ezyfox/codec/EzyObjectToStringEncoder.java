package com.tvd12.ezyfox.codec;

public interface EzyObjectToStringEncoder extends EzyObjectToByteEncoder {

	<T> T encode(Object msg, Class<T> outType) throws Exception;
	
}
