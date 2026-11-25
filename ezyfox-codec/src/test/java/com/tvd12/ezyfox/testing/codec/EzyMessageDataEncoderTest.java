package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyDeflateCompressor;
import com.tvd12.ezyfox.codec.EzyMessageDataEncoder;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;

public class EzyMessageDataEncoderTest {

    @Test
    public void test() {
        EzyMessageDataEncoder sut = new A();
        Asserts.assertThat(() -> sut.toMessageContent(null))
            .willThrows(UnsupportedOperationException.class);
        Asserts.assertThat(() -> sut.encryptMessageContent(null, null))
            .willThrows(UnsupportedOperationException.class);
    }

    @Test
    public void compressMessageContentTest() throws Exception {
        // given
        EzyMessageDataEncoder sut = new A();
        byte[] content = "hello hello hello hello hello".getBytes(StandardCharsets.UTF_8);

        // when
        byte[] actual = sut.compressMessageContent(content);

        // then
        Asserts.assertEquals(EzyDeflateCompressor.decompress(actual, 0), content);
    }

    @Test
    public void encryptMessageContentCompressedTest() {
        // given
        EzyMessageDataEncoder sut = new B();

        // when
        Throwable e = Asserts.assertThrows(() ->
            sut.encryptMessageContent(new byte[]{1}, new byte[]{2}, true)
        );

        // then
        Asserts.assertEqualsType(e, UnsupportedOperationException.class);
    }

    @Test
    public void encryptMessageContentNotCompressedTest() throws Exception {
        // given
        EzyMessageDataEncoder sut = new B();
        byte[] content = new byte[]{1, 2, 3};

        // when
        byte[] actual = sut.encryptMessageContent(content, new byte[]{2}, false);

        // then
        Asserts.assertEquals(actual, content);
    }

    private static class A implements EzyMessageDataEncoder {
        @Override
        public byte[] encode(Object msg) {
            return null;
        }
    }

    private static class B extends A {
        @Override
        public byte[] encryptMessageContent(byte[] messageContent, byte[] encryptionKey) {
            return messageContent;
        }
    }
}
