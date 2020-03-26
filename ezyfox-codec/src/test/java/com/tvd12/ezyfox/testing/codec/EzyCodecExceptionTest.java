package com.tvd12.ezyfox.testing.codec;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.exception.EzyCodecException;

public class EzyCodecExceptionTest {

	@Test
	public void test() {
		new EzyCodecException("msg", new Exception());
	}
	
}
