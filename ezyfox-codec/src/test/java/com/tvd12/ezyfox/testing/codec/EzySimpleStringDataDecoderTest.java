package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzySimpleStringDataDecoder;
import com.tvd12.ezyfox.codec.EzyStringToObjectDecoder;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

public class EzySimpleStringDataDecoderTest {

    @Test
    public void test() throws Exception {
        EzyStringToObjectDecoder d = mock(EzyStringToObjectDecoder.class);
        when(d.decode(anyString())).then(invocation ->
            invocation.getArguments()[0]
        );
        when(d.decode(any(byte[].class))).thenAnswer(invocation -> new String(invocation.getArgumentAt(0, byte[].class)));
        EzySimpleStringDataDecoder decoder = new EzySimpleStringDataDecoder(d);
        decoder.decode("abc", data -> {
            assert data.equals("abc");
        });
        decoder.decode("abc".getBytes(), data -> {
            assert data.equals("abc");
        });
        decoder.destroy();
    }
}
