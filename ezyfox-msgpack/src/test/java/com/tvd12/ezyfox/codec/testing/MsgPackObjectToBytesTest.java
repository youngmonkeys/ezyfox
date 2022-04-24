package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.EzyMessageSerializer;
import com.tvd12.ezyfox.codec.MsgPackObjectToBytes;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

public class MsgPackObjectToBytesTest extends BaseTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test() {
        EzyMessageSerializer serializer = mock(EzyMessageSerializer.class);
        when(serializer.serialize(any())).thenAnswer(invocation -> {
            throw new Exception();
        });
        MsgPackObjectToBytes instance = new MsgPackObjectToBytes(serializer);
        instance.convert(new Object());
    }
}
