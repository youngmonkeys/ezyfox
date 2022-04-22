package com.tvd12.ezyfox.codec.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.EzyMessageSerializer;
import com.tvd12.ezyfox.codec.MsgPackObjectToBytes;
import com.tvd12.test.base.BaseTest;
import static org.mockito.Mockito.*;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class MsgPackObjectToBytesTest extends BaseTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test() {
        EzyMessageSerializer serializer = mock(EzyMessageSerializer.class);
        when(serializer.serialize(any())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                throw new Exception();
            }
        });
        MsgPackObjectToBytes instance = new MsgPackObjectToBytes(serializer);
        instance.convert(new Object());
    }

}
