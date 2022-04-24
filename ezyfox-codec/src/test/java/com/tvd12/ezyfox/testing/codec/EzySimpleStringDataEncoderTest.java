package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyObjectToStringEncoder;
import com.tvd12.ezyfox.codec.EzySimpleStringDataEncoder;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

public class EzySimpleStringDataEncoderTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() throws Exception {
        EzyObjectToStringEncoder e = mock(EzyObjectToStringEncoder.class);
        when(e.encode(anyObject())).thenAnswer(invocation ->
            invocation.getArguments()[0].toString().getBytes()
        );
        when(
            e.encode(anyObject(), any(Class.class))
        )
            .thenAnswer(invocation -> invocation.getArguments()[0]);
        EzySimpleStringDataEncoder encoder = new EzySimpleStringDataEncoder(e);
        assert encoder.encode("abc").length == 3;
        assert encoder.encode("abc", String.class).equals("abc");
    }
}
