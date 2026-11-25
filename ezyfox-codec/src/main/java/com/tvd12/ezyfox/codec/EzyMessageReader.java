package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.exception.EzyMaxRequestSizeException;
import com.tvd12.ezyfox.exception.EzyZeroRequestSizeException;

import java.util.Arrays;

public abstract class EzyMessageReader<B> {

    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    private static final int MIN_CONTENT_CAPACITY = 1024;
    protected int size;
    protected byte[] content;
    protected int contentOffset;
    protected int contentLength;
    protected byte headerByte;
    protected byte[] sizeBytes;
    protected EzyMessageHeader header;

    public EzyMessageReader() {
        clear();
    }

    protected abstract int remaining(B buffer);

    protected abstract byte readByte(B buffer);

    protected abstract int readMessageSize(B buffer);

    protected abstract void readMessageContent(B buffer, byte[] content, int offset, int length);

    public boolean readHeader(B buffer) {
        int remaining = remaining(buffer);
        if (remaining < getHeaderLength()) {
            return false;
        }
        headerByte = readByte(buffer);
        readHeader(headerByte);
        return true;
    }

    private void readHeader(byte header) {
        this.header = EzyMessageHeaderReader.read(header);
    }

    public boolean readSize(B buffer, int maxSize) {
        int remaining = remaining(buffer);
        if (remaining < getSizeLength()) {
            return false;
        }
        long unsignedSize = readMessageSize(buffer) & 0xFFFFFFFFL;
        if (unsignedSize == 0) {
            throw new EzyZeroRequestSizeException();
        }
        if (unsignedSize > maxSize) {
            throw new EzyMaxRequestSizeException(unsignedSize, maxSize);
        }
        this.size = (int) unsignedSize;
        return true;
    }

    public boolean readContent(B buffer) {
        int remaining = remaining(buffer);
        if (remaining <= 0) {
            return false;
        }
        if (content == EMPTY_BYTE_ARRAY) {
            prepareContent(remaining);
        }
        int length = Math.min(remaining, contentLength - contentOffset);
        ensureContentCapacity(contentOffset + length);
        readMessageContent(buffer, content, contentOffset, length);
        contentOffset += length;
        return contentOffset == contentLength;
    }

    private void prepareContent(int remaining) {
        int prefixLength = 0;
        if (isRawBytes()) {
            int headerLength = getHeaderLength();
            prefixLength = headerLength + sizeBytes.length;
        }
        this.contentLength = prefixLength + size;
        int capacity = Math.min(
            contentLength,
            prefixLength + Math.max(remaining, MIN_CONTENT_CAPACITY)
        );
        this.content = new byte[capacity];
        if (prefixLength > 0) {
            int headerLength = getHeaderLength();
            this.content[0] = headerByte;
            System.arraycopy(sizeBytes, 0, content, headerLength, sizeBytes.length);
        }
        this.contentOffset = prefixLength;
    }

    private void ensureContentCapacity(int required) {
        if (required <= content.length) {
            return;
        }
        int newCapacity = Math.max(required, content.length << 1);
        if (newCapacity < 0 || newCapacity > contentLength) {
            newCapacity = contentLength;
        }
        this.content = Arrays.copyOf(content, newCapacity);
    }

    public void clear() {
        this.size = 0;
        this.headerByte = 0;
        this.sizeBytes = EMPTY_BYTE_ARRAY;
        this.content = EMPTY_BYTE_ARRAY;
        this.contentOffset = 0;
        this.contentLength = 0;
    }

    public EzyMessage get() {
        return new EzySimpleMessage(header, content, size);
    }

    protected int getSizeLength() {
        return header.isBigSize() ? 4 : 2;
    }

    protected boolean isRawBytes() {
        return header.isRawBytes();
    }

    protected int getHeaderLength() {
        return 1;
    }
}
