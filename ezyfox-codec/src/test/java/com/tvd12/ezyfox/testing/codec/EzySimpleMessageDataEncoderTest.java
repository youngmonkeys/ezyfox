package com.tvd12.ezyfox.testing.codec;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.EzyObjectToByteEncoder;
import com.tvd12.ezyfox.codec.EzySimpleMessageDataEncoder;
import com.tvd12.test.util.RandomUtil;

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

    @Test
    public void toMessageContentTest() throws Exception {
        // given
        EzyObjectToByteEncoder encoder = mock(EzyObjectToByteEncoder.class);
        EzySimpleMessageDataEncoder sut = new EzySimpleMessageDataEncoder(encoder);
        Object data = new Object();

        // when
        sut.toMessageContent(data);

        // then
        verify(encoder, times(1)).toMessageContent(data);
    }

    @Test
    public void encryptMessageContent() throws Exception {
        // given
        EzyObjectToByteEncoder encoder = mock(EzyObjectToByteEncoder.class);
        EzySimpleMessageDataEncoder sut = new EzySimpleMessageDataEncoder(encoder);
        byte[] messageContent = RandomUtil.randomShortByteArray();
        byte[] encryptionKey = RandomUtil.randomShortByteArray();

        // when
        sut.encryptMessageContent(messageContent, encryptionKey);

        // then
        verify(encoder, times(1)).encryptMessageContent(messageContent, encryptionKey);
    }
}
