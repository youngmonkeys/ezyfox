package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyMessage;
import com.tvd12.ezyfox.codec.EzyMessageReaders;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyMessageReadersTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyMessageReaders.class;
    }

    @Test
    public void test() {
        byte[] bytes = new byte[]{0};
        assert EzyMessageReaders.bytesToMessage(bytes) == null;
        bytes = new byte[]{1, 100};
        assert EzyMessageReaders.bytesToMessage(bytes) == null;
        bytes = new byte[]{0, 0, 3, 1, 1};
        assert EzyMessageReaders.bytesToMessage(bytes) == null;
        bytes = new byte[]{0, 0, 3, 1, 1, 1};
        assert EzyMessageReaders.bytesToMessage(bytes) != null;
        bytes = new byte[]{0, 0, 3, 1, 1, 1, 1};
        assert EzyMessageReaders.bytesToMessage(bytes) != null;
    }

    @Test
    public void nullOrEmptyBytesTest() {
        Asserts.assertNull(EzyMessageReaders.bytesToMessage(null));
        Asserts.assertNull(EzyMessageReaders.bytesToMessage(new byte[0]));
    }

    @Test
    public void zeroSizeTest() {
        Asserts.assertNull(
            EzyMessageReaders.bytesToMessage(new byte[]{0, 0, 0, 1})
        );
        Asserts.assertNull(
            EzyMessageReaders.bytesToMessage(new byte[]{1, 0, 0, 0, 0, 1})
        );
    }

    @Test
    public void sizeWithHighestBitTest() {
        Asserts.assertNull(
            EzyMessageReaders.bytesToMessage(
                new byte[]{1, (byte) 0x80, 0, 0, 0, 1, 2, 3}
            )
        );
        Asserts.assertNull(
            EzyMessageReaders.bytesToMessage(
                new byte[]{1, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, 1}
            )
        );
        Asserts.assertNull(
            EzyMessageReaders.bytesToMessage(
                new byte[]{0, (byte) 0xFF, (byte) 0xFF, 1}
            )
        );
    }

    @Test
    public void readMessageTest() {
        // given
        byte[] bytes = new byte[]{1, 0, 0, 0, 3, 7, 8, 9, 10};

        // when
        EzyMessage message = EzyMessageReaders.bytesToMessage(bytes);

        // then
        Asserts.assertEquals(message.getSize(), 3);
        Asserts.assertEquals(message.getContent(), new byte[]{7, 8, 9});
        Asserts.assertTrue(message.getHeader().isBigSize());
    }

    @Test
    public void readRawBytesMessageTest() {
        // given
        byte header = (byte) (1 | (1 << 4));
        byte[] bytes = new byte[]{header, 0, 0, 0, 3, 7, 8, 9, 10};

        // when
        EzyMessage message = EzyMessageReaders.bytesToMessage(bytes);

        // then
        Asserts.assertEquals(message.getSize(), 3);
        Asserts.assertEquals(
            message.getContent(),
            new byte[]{header, 0, 0, 0, 3, 7, 8, 9}
        );
        Asserts.assertEquals(message.getContentStartIndex(), 0);
        Asserts.assertEquals(message.getByteCount(), 8);
        Asserts.assertTrue(message.getHeader().isRawBytes());
    }
}
