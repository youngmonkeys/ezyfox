package com.tvd12.ezyfox.testing.codec;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.EzyObjectToByteEncoder;
import com.tvd12.ezyfox.codec.EzySimpleMessageDataEncoder;
import static org.mockito.Mockito.*;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class EzySimpleMessageDataEncoderTest {

	@Test
	public void test() throws Exception {
		EzyObjectToByteEncoder e = mock(EzyObjectToByteEncoder.class);
		when(e.encode(any())).thenAnswer(new Answer<Object>() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				return invocation.getArguments()[0].toString().getBytes();
			}
		});
		EzySimpleMessageDataEncoder encoder = new EzySimpleMessageDataEncoder(e);
		assert encoder.encode("abc").length == 3;
	}
	
}
