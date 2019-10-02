package com.tvd12.ezyfox.testing.codec;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.EzyObjectToStringEncoder;
import com.tvd12.ezyfox.codec.EzySimpleStringDataEncoder;
import static org.mockito.Mockito.*;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class EzySimpleStringDataEncoderTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test() throws Exception {
		EzyObjectToStringEncoder e = mock(EzyObjectToStringEncoder.class);
		when(e.encode(anyObject())).thenAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return invocation.getArguments()[0].toString().getBytes();
			}
		});
		when(e.encode(anyObject(), any(Class.class))).thenAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return invocation.getArguments()[0];
			}
		});
		EzySimpleStringDataEncoder encoder = new EzySimpleStringDataEncoder(e);
		assert encoder.encode("abc").length == 3;
		assert encoder.encode("abc", String.class).equals("abc");
	}
	
}
