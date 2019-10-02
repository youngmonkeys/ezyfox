package com.tvd12.ezyfox.testing.codec;

import java.nio.ByteBuffer;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.EzyObjectDeserializer;

@SuppressWarnings("unchecked")
public class EzyObjectDeserializerTest {

	@Test
	public void test() {
		ExEzyObjectDeserializer deserializer = new ExEzyObjectDeserializer();
		ByteBuffer buffer = ByteBuffer.allocate(1);
		assert deserializer.read(buffer) == buffer;
		assert deserializer.read(new byte[] {1}) != null;
		assert deserializer.deserialize(new byte[] {1}) != null;
		assert deserializer.deserialize("abc") != null;
	}
	
	public static class ExEzyObjectDeserializer implements EzyObjectDeserializer {
		@Override
		public <T> T deserialize(ByteBuffer buffer) {
			return (T)buffer;
		}
	}
	
}
