package com.tvd12.ezyfox.codec;

import java.nio.ByteBuffer;

import com.tvd12.ezyfox.codec.EzyMessageReader;
import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.ezyfox.io.EzyInts;

public class EzyByteBufferMessageReader extends EzyMessageReader<ByteBuffer> {

	@Override
	protected byte readByte(ByteBuffer buffer) {
		return buffer.get();
	}
	
	@Override
	protected int remaining(ByteBuffer buffer) {
		return buffer.remaining();
	}
	
	@Override
	protected int readMessgeSize(ByteBuffer buffer) {
		return EzyInts.bin2uint(EzyBytes.copy(buffer, getSizeLength()));
	}
	
	@Override
	protected void readMessageContent(ByteBuffer buffer, byte[] content) {
		buffer.get(content);
	}

}
