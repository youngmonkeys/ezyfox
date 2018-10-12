package com.tvd12.ezyfox.codec;

public interface EzyObjectToByteEncoder {

	byte[] encode(Object msg) throws Exception;
	
}
