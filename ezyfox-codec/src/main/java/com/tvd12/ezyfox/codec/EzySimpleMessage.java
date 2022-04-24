package com.tvd12.ezyfox.codec;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class EzySimpleMessage implements EzyMessage {

    private final int size;
    private final byte[] content;
    private final EzyMessageHeader header;
    private final int byteCount;

    public EzySimpleMessage(EzyMessageHeader header, byte[] content, int size) {
        this.header = header;
        this.content = content;
        this.size = size;
        this.byteCount = 1 + getSizeLength() + content.length;
    }

    @Override
    public boolean hasBigSize() {
        return header.isBigSize();
    }

    @Override
    public int getContentStartIndex() {
        if (header.isRawBytes()) {
            return 0;
        }
        return 1 + getSizeLength();
    }

    @Override
    public String toString() {
        return "(" +
            "header: " +
            header +
            ", " +
            "size: " +
            size +
            ", " +
            "byteCount: " +
            byteCount +
            ", " +
            "content: " +
            Arrays.toString(content) +
            ")";
    }
}
