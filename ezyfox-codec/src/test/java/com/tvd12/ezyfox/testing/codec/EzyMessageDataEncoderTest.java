package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyMessageDataEncoder;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

public class EzyMessageDataEncoderTest {

    @Test
    public void test() {
        EzyMessageDataEncoder sut = new A();
        Asserts.assertThat(() -> sut.toMessageContent(null))
            .willThrows(UnsupportedOperationException.class);
        Asserts.assertThat(() -> sut.encryptMessageContent(null, null))
            .willThrows(UnsupportedOperationException.class);
    }

    private static class A implements EzyMessageDataEncoder {

        @Override
        public byte[] encode(Object msg) {
            return null;
        }
    }
}
