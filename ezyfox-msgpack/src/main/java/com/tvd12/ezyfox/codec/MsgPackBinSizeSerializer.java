package com.tvd12.ezyfox.codec;

import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_BIN16_SIZE;
import static com.tvd12.ezyfox.codec.MsgPackConstant.MAX_BIN8_SIZE;

import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.ezyfox.io.EzyCastToByte;

public class MsgPackBinSizeSerializer implements EzyCastToByte {
	
	private static final MsgPackBinSizeSerializer INSTANCE = new MsgPackBinSizeSerializer();
	
	private MsgPackBinSizeSerializer() {}

	public static MsgPackBinSizeSerializer getInstance() {
		return INSTANCE;
	}
	
	public byte[] serialize(int size) {
		if(size <= MAX_BIN8_SIZE)
			return parse8(size);
		if(size <= MAX_BIN16_SIZE)
			return parse16(size);
		return parse32(size);
	}
	
	private byte[] parse8(int size) {
		return new byte[] {
			cast(0xc4), cast(size)
		};
	}
	
	private byte[] parse16(int size) {
		return EzyBytes.getBytes(0xc5 , size, 2);
	}
	
	private byte[] parse32(int size) {
		return EzyBytes.getBytes(0xc6 , size, 4);
	}
}