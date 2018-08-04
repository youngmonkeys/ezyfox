package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.codec.EzyAbstractSerializer;

public abstract class EzyAbstractToBytesSerializer extends EzyAbstractSerializer<byte[]> { 
 
	@Override 
	public byte[] serialize(Object value) {
		return value == null
				? parseNil() 
				: parseNotNull(value);
	} 
	 
} 
