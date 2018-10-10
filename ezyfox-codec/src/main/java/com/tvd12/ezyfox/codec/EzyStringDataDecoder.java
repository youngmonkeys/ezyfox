package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.callback.EzyCallback;
import com.tvd12.ezyfox.util.EzyDestroyable;

public interface EzyStringDataDecoder extends EzyDestroyable {
	
	void decode(String bytes, EzyCallback<Object> callback) throws Exception;

	void decode(byte[] bytes, int offset, int len, EzyCallback<Object> callback) throws Exception;
	
}
