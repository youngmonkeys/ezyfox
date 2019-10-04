package com.tvd12.ezyfox.codec.testing;

import java.nio.ByteBuffer;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvd12.ezyfox.codec.EzyMessageDeserializer;
import com.tvd12.ezyfox.codec.JacksonSimpleDeserializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.test.base.BaseTest;

public class JacksonSimpleDeserializerTest extends BaseTest {

	private ObjectMapper objectMapper 
			= new ObjectMapper();
	private EzyMessageDeserializer deserializer 
			= new JacksonSimpleDeserializer(objectMapper);
	
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void test() {
		EzyArray a1 = deserializer.deserialize(ByteBuffer.wrap(new byte[] {1, 2, 3}));
		System.out.print("a1: " + a1);
	}
	
	@Test
	public void test2() {
		EzyArray a1 = deserializer.deserialize("[1, 2, 3]");
		System.out.println(a1);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void test3() {
		EzyArray a1 = deserializer.deserialize("1, 2, 3");
		System.out.println(a1);
	}
	
}
