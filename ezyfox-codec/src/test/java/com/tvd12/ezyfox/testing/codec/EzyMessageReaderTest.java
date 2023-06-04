package com.tvd12.ezyfox.testing.codec;

import com.tvd12.ezyfox.codec.EzyMessageHeader;
import com.tvd12.ezyfox.codec.EzyMessageReader;
import com.tvd12.ezyfox.exception.EzyZeroRequestSizeException;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.util.RandomUtil;
import org.testng.annotations.Test;

import static org.mockito.Mockito.mock;

public class EzyMessageReaderTest {

    @Test
    public void readSizeErrorDueToZero() {
        // given
        ZeroSizeEzyMessageReader instance = new ZeroSizeEzyMessageReader();
        Object buffer = new Object();
        int maxSize = RandomUtil.randomSmallInt();

        // when
        Throwable e = Asserts.assertThrows(() ->
            instance.readSize(buffer, maxSize)
        );

        // then
        Asserts.assertEqualsType(e, EzyZeroRequestSizeException.class);
    }

    private static class ZeroSizeEzyMessageReader extends EzyMessageReader<Object> {

        @Override
        protected int getSizeLength() {
            return 4;
        }

        @Override
        protected int remaining(Object buffer) {
            return 5;
        }

        @Override
        protected byte readByte(Object buffer) {
            return 0;
        }

        @Override
        protected int readMessageSize(Object buffer) {
            return 0;
        }

        @Override
        protected void readMessageContent(
            Object buffer,
            byte[] content,
            int offset,
            int length
        ) {}
    }
}
