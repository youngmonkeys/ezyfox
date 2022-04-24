package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyMessage;
import com.tvd12.ezyfox.codec.EzyObjectToMessage;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

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
