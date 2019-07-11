package com.tvd12.ezyfox.codec.testing;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.codec.EzyMessageSerializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleDeserializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleSerializer;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.io.EzyMath;

public class MsgPackMySimpleDeserializer5Test extends MsgPackCodecTest {

	@Test
	public void test1() throws IOException {
		int size = EzyMath.bin2int(16);
		EzyArrayBuilder builder = newArrayBuilder();
		for(int i = 0 ; i < size ; ++i)
//		for(int i = 1 ; i < 500 ; ++i) 
			builder.append(i);
//		builder.append(300);
		EzyMessageSerializer serializer = new MsgPackSimpleSerializer();
		byte[] bytes = serializer.serialize(builder.build());
		MsgPackSimpleDeserializer deserializer = new MsgPackSimpleDeserializer();
		EzyArray answer = deserializer.deserialize(bytes);
		Assert.assertEquals(answer.get(size - 1), new Integer(size - 1));
	}
	
}
