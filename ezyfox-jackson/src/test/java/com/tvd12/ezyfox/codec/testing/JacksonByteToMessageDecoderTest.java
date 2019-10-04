package com.tvd12.ezyfox.codec.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.JacksonByteToMessageDecoder;
import com.tvd12.ezyfox.codec.JacksonCodecCreator;

public class JacksonByteToMessageDecoderTest {

	@Test
	public void test() throws Exception {
		JacksonCodecCreator creator = new JacksonCodecCreator();
		JacksonByteToMessageDecoder decoder = (JacksonByteToMessageDecoder) creator.newDecoder(128);
		System.out.println(decoder.decode("[1, 2, 3]"));
		System.out.println(decoder.decode("[1, 2, 3]".getBytes()));
	}
	
}
