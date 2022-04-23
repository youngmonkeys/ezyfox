package com.tvd12.ezyfox.stream;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class EzyInputStreams {

    public static final int EOF = -1;
    public static final int DEFAULT_BUFFER_SIZE = 1024;

    private EzyInputStreams() {}

    public static byte[] toByteArray(InputStream stream)
        throws IOException {
        return toByteArray(stream, DEFAULT_BUFFER_SIZE);
    }

    public static byte[] toByteArray(
        InputStream stream,
        int bufferSize
    ) throws IOException {
        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[bufferSize];
            int readBytes;
            while ((readBytes = stream.read(buffer)) != EOF) {
                outStream.write(buffer, 0, readBytes);
            }
            return outStream.toByteArray();
        }
    }

    public static String toStringUtf8(InputStream stream) throws IOException {
        return toString(stream, StandardCharsets.UTF_8);
    }

    public static String toString(InputStream stream, Charset charset)
        throws IOException {
        byte[] bytes = toByteArray(stream);
        return new String(bytes, charset);
    }

    public static List<String> toLines(InputStream stream)
        throws IOException {
        return toLines(stream, Charset.defaultCharset());
    }

    public static List<String> toLines(InputStream stream, String charset)
        throws IOException {
        return toLines(stream, Charset.forName(charset));
    }

    public static List<String> toLines(InputStream stream, Charset charset)
        throws IOException {
        try (
            BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(stream, charset)
            )
        ) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        }
    }
}
