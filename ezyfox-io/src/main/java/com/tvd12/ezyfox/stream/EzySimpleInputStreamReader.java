package com.tvd12.ezyfox.stream;

import com.tvd12.ezyfox.builder.EzyBuilder;

import java.io.InputStream;
import java.util.List;

import static com.tvd12.ezyfox.util.EzyReturner.returnWithIllegalArgumentException;

public class EzySimpleInputStreamReader implements EzyInputStreamReader {

    @Override
    public byte[] readBytes(InputStream stream) {
        try {
            return EzyInputStreams.toByteArray(stream);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public String readString(InputStream stream, String charset) {
        return returnWithIllegalArgumentException(() -> new String(readBytes(stream), charset));
    }

    @Override
    public char[] readChars(InputStream stream, String charset) {
        return readString(stream, charset).toCharArray();
    }

    @Override
    public List<String> readLines(InputStream stream, String charset) {
        try {
            return EzyInputStreams.toLines(stream, charset);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements EzyBuilder<EzyInputStreamReader> {

        @Override
        public EzyInputStreamReader build() {
            return new EzySimpleInputStreamReader();
        }
    }
}
