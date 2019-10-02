package com.tvd12.ezyfox.testing.codec;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.EzyMessageHeaderReader;

public class EzyMessageHeaderReaderTest {

	@Test
	public void test() {
		assert EzyMessageHeaderReader.readEncrypted((byte)((1 << 1) & 0xFF));
		assert EzyMessageHeaderReader.readCompressed((byte)((1 << 2) & 0xFF));
		assert EzyMessageHeaderReader.readText((byte)((1 << 3) & 0xFF));
	}
	
}
