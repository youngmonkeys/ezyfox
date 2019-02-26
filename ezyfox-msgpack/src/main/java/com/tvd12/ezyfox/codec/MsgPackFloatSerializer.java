package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.ezyfox.io.EzyCastToByte;

public class MsgPackFloatSerializer implements EzyCastToByte {
	
	private static final MsgPackFloatSerializer INSTANCE = new MsgPackFloatSerializer();
	
	private MsgPackFloatSerializer() {}

	public static MsgPackFloatSerializer getInstance() {
		return INSTANCE;
	}
	
	public byte[] serialize(float value) {
		return EzyBytes.getBytes(cast(0xca), value);
	}
	
}