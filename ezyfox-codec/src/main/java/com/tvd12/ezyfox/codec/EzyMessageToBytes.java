package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.codec.EzyMessage;

public interface EzyMessageToBytes {

	<T> T convert(EzyMessage message);
	
}
