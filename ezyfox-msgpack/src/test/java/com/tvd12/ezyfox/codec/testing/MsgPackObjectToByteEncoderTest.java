package com.tvd12.ezyfox.codec.testing;

import com.tvd12.ezyfox.codec.EzyMessageToBytes;
import com.tvd12.ezyfox.codec.EzyObjectToMessage;
import com.tvd12.ezyfox.codec.MsgPackObjectToByteEncoder;

import org.testng.annotations.Test;
import static org.mockito.Mockito.*;

public class MsgPackObjectToByteEncoderTest {

    @Test
    public void test() throws Exception {
        EzyMessageToBytes messageToBytes = mock(EzyMessageToBytes.class);
        EzyObjectToMessage objectToMessage = mock(EzyObjectToMessage.class);
        MsgPackObjectToByteEncoder encoder = new MsgPackObjectToByteEncoder(
                messageToBytes, objectToMessage);
        encoder.encode(new Object());
    }
}