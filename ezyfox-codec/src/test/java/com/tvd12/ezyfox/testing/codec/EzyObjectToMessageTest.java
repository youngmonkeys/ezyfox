package com.tvd12.ezyfox.testing.codec;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.EzyMessage;
import com.tvd12.ezyfox.codec.EzyObjectToMessage;
import com.tvd12.test.assertion.Asserts;

public class EzyObjectToMessageTest {

    @Test
    public void test() {
        EzyObjectToMessage sut = new A();
        Asserts.assertThat(() -> sut.convertToMessageContent(null))
            .willThrows(UnsupportedOperationException.class);
        Asserts.assertThat(() -> sut.packToMessage(null, false))
        .willThrows(UnsupportedOperationException.class);
    }

    private static class A implements EzyObjectToMessage {

        @Override
        public EzyMessage convert(Object object) {
            return null;
        }

    }
}