package com.tvd12.ezyfox.codec;

public interface EzyStringDataEncoder extends EzyMessageDataEncoder {

	<T> T encode(Object data, Class<T> outType) throws Exception;
	
}
