package com.tvd12.ezyfox.codec.testing;

import java.nio.ByteBuffer;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.MsgPackSimpleDeserializer;
import com.tvd12.ezyfox.codec.MsgPackSimpleSerializer;

public class MsgPackSimpleDerSerTest {

	private final MsgPackSimpleSerializer ser = new MsgPackSimpleSerializer();
	private final MsgPackSimpleDeserializer der = new MsgPackSimpleDeserializer();
	
	@Test
	public void serializeNegateInt32Case() {
		// given
		int number = -765067265;
		
		// when
		byte[] bytes = ser.serialize(number);
		int actual = der.deserialize(bytes);
		
		//then
		assert bytes.length == 5;
		assert actual == number;
		ByteBuffer buffer = ByteBuffer.allocate(5);
		buffer.put((byte)0xd2);
		buffer.putInt(number);
		buffer.flip();
		assert buffer.equals(ByteBuffer.wrap(bytes));
	}
	
}
