package com.tvd12.ezyfox.codec;

public interface EzyMessageDataEncoder {

	byte[] encode(Object data) throws Exception;
	
}
