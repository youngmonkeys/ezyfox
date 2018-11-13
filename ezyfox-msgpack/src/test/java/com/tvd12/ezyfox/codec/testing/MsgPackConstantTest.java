package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.MsgPackConstant;
import com.tvd12.test.base.BaseTest;

public class MsgPackConstantTest extends BaseTest {

	@Override
	public Class<?> getTestClass() {
		return MsgPackConstant.class;
	}
	
	public static void main(String[] args) {
		System.out.println(MsgPackConstant.MAX_SMALL_MESSAGE_SIZE);
	}
}
