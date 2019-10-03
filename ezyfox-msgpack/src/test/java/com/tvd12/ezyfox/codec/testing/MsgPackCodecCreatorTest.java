package com.tvd12.ezyfox.codec.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.MsgPackCodecCreator;

public class MsgPackCodecCreatorTest {

	@Test
	public void test() {
		MsgPackCodecCreator codecCreator = new MsgPackCodecCreator();
		codecCreator.newDecoder(100);
		codecCreator.newEncoder();
	}
	
}
