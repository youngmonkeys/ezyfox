package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyMessage;
import com.tvd12.ezyfox.codec.EzyObjectToMessage;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;

public class EzyObjectToMessageTest {

    @Test
    public void test() {
        EzyObjectToMessage sut = new A();
        Asserts.assertThat(() -> sut.convertToMessageContent(null))
            .willThrows(UnsupportedOperationException.class);
        Asserts.assertThat(() -> sut.packToMessage(null, false))
            .willThrows(UnsupportedOperationException.class);
    }

    @Test
    public void packToMessageCompressedTest() {
        // given
        EzyMessage message = mock(EzyMessage.class);
        EzyObjectToMessage sut = new B(message);

        // when
        Throwable e = Asserts.assertThrows(() ->
            sut.packToMessage(new byte[]{1}, false, true)
        );

        // then
        Asserts.assertEqualsType(e, UnsupportedOperationException.class);
    }

    @Test
    public void packToMessageNotCompressedTest() {
        // given
        EzyMessage message = mock(EzyMessage.class);
        EzyObjectToMessage sut = new B(message);

        // when
        EzyMessage actual = sut.packToMessage(new byte[]{1}, true, false);

        // then
        Asserts.assertEquals(actual, message);
    }

    private static class A implements EzyObjectToMessage {
        @Override
        public EzyMessage convert(Object object) {
            return null;
        }
    }

    private static class B extends A {

        private final EzyMessage message;

        private B(EzyMessage message) {
            this.message = message;
        }

        @Override
        public EzyMessage packToMessage(byte[] content, boolean encrypted) {
            return message;
        }
    }
}
