package com.tvd12.ezyfox.testing.codec;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.callback.EzyCallback;
import com.tvd12.ezyfox.codec.EzySimpleStringDataDecoder;
import com.tvd12.ezyfox.codec.EzyStringToObjectDecoder;
import static org.mockito.Mockito.*;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class EzySimpleStringDataDecoderTest {

    @Test
    public void test() throws Exception {
        EzyStringToObjectDecoder d = mock(EzyStringToObjectDecoder.class);
        when(d.decode(anyString())).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0];
            }
        });
        when(d.decode(any(byte[].class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return new String(invocation.getArgumentAt(0, byte[].class));
            }
        });
        EzySimpleStringDataDecoder decoder = new EzySimpleStringDataDecoder(d);
        decoder.decode("abc", new EzyCallback<Object>() {

            @Override
            public void call(Object data) {
                assert data.equals("abc");
            }
        });
        decoder.decode("abc".getBytes(), new EzyCallback<Object>() {
            @Override
            public void call(Object data) {
                assert data.equals("abc");
            }
        });
        decoder.destroy();

    }
}
