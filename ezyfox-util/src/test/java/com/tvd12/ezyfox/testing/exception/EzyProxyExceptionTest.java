package com.tvd12.ezyfox.testing.exception;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.exception.EzyProxyException;

public class EzyProxyExceptionTest {

	@Test
	public void test() {
		new EzyProxyException(new Exception());
	}
	
}
