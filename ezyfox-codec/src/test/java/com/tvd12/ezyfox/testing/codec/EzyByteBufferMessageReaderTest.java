package com.tvd12.ezyfox.testing.codec;

import java.nio.ByteBuffer;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.EzyByteBufferMessageReader;
import com.tvd12.ezyfox.exception.EzyMaxRequestSizeException;

public class EzyByteBufferMessageReaderTest {

	@Test
	public void test() {
		EzyByteBufferMessageReader reader = new EzyByteBufferMessageReader();
		ByteBuffer buffer = ByteBuffer.allocate(12);
		buffer.put((byte)0);
		buffer.putShort((short)9);
		buffer.put("012345678".getBytes());
		buffer.flip();
		assert reader.readHeader(buffer);
		assert reader.readSize(buffer, 128);
		assert reader.readContent(buffer);
		assert reader.get().getSize() == 9;
		assert !reader.readHeader(buffer);
		assert !reader.readSize(buffer, 128);
		assert !reader.readContent(buffer);
	}
	
	@Test
	public void test2() {
		EzyByteBufferMessageReader reader = new EzyByteBufferMessageReader();
		ByteBuffer buffer = ByteBuffer.allocate(12);
		byte header = (byte)0;
		header |= (1 << 0) & 0xFF;
		buffer.put(header);
		buffer.putInt(7);
		buffer.put("0123456".getBytes());
		buffer.flip();
		assert reader.readHeader(buffer);
		assert reader.readSize(buffer, 128);
	}
	
	@Test(expectedExceptions = EzyMaxRequestSizeException.class)
	public void test3() {
		EzyByteBufferMessageReader reader = new EzyByteBufferMessageReader();
		ByteBuffer buffer = ByteBuffer.allocate(12);
		byte header = (byte)0;
		header |= (1 << 0) & 0xFF;
		buffer.put(header);
		buffer.putInt(7);
		buffer.put("0123456".getBytes());
		buffer.flip();
		reader.readHeader(buffer);
		reader.readSize(buffer, 1);
	}
	
	@Test
	public void testRawBytes() {
		EzyByteBufferMessageReader reader = new EzyByteBufferMessageReader();
		ByteBuffer buffer = ByteBuffer.allocate(12);
		byte header = (byte)0;
		header |= (1 << 0) & 0xFF;
		header |= (1 << 4) & 0xFF;
		buffer.put(header);
		buffer.putInt(7);
		buffer.put("0123456".getBytes());
		buffer.flip();
		reader.readHeader(buffer);
		reader.readSize(buffer, 128);
		reader.readContent(buffer);
	}
	
}
