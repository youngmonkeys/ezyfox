package com.tvd12.ezyfox.testing.exception;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.exception.EzyNotFoundException;

public class EzyNotFoundExceptionTest {

	@Test
	public void test() {
		new EzyNotFoundException();
		new EzyNotFoundException("");
		new EzyNotFoundException(new Exception());
		new EzyNotFoundException("", new Exception());
	}
	
}
