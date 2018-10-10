package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.callback.EzyCallback;
import com.tvd12.ezyfox.util.EzyDestroyable;

public interface EzyMessageDataDecoder extends EzyDestroyable {

	Object decode(EzyMessage message) throws Exception;
	
	void decode(byte[] bytes, EzyCallback<EzyMessage> callback) throws Exception;
	
}
