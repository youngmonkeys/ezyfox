package com.tvd12.ezyfox.testing.codec;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.EzyMessage;
import com.tvd12.ezyfox.codec.EzyMessageHeader;

public class EzyMessageTest {

    @Test
    public void test() {
        EzyMessage message = new ExEzyMessage(true);
        assert message.getSizeLength() == 4;
        EzyMessage message2 = new ExEzyMessage(false);
        assert message2.getSizeLength() == 2;
    }

    public static class ExEzyMessage implements EzyMessage {

        protected final boolean hasBigSize;

        public ExEzyMessage(boolean h) {
            this.hasBigSize = h;
        }

        @Override
        public int getSize() {
            return 0;
        }

        @Override
        public byte[] getContent() {
            return null;
        }

        @Override
        public EzyMessageHeader getHeader() {
            return null;
        }

        @Override
        public int getByteCount() {
            return 0;
        }

        @Override
        public boolean hasBigSize() {
            return hasBigSize;
        }

        @Override
        public int getContentStartIndex() {
            return 0;
        }

    }

}
