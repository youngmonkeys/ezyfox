package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.callback.EzyCallback;
import com.tvd12.ezyfox.util.EzyDestroyable;
import com.tvd12.ezyfox.util.EzyResettable;

public interface EzyMessageDataDecoder extends EzyResettable, EzyDestroyable {

	void decode(byte[] bytes, EzyCallback<EzyMessage> callback) throws Exception;
	
	Object decode(EzyMessage message, byte[] decryptionKey) throws Exception;
	
}
