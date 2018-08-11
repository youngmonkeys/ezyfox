package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.codec.EzyMessage;

public interface EzyObjectToMessage {

	EzyMessage convert(Object object);
	
}
