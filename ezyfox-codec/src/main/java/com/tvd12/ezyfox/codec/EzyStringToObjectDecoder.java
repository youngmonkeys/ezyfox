package com.tvd12.ezyfox.codec;

import java.nio.ByteBuffer;

public interface EzyStringToObjectDecoder {
	
	Object decode(String bytes) throws Exception;

	Object decode(ByteBuffer bytes) throws Exception;
	
}
