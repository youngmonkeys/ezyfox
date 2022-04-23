package com.tvd12.ezyfox.file;

import com.tvd12.ezyfox.builder.EzyBuilder;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class EzySimpleFileReader implements EzyFileReader {

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public byte[] readBytes(File file) {
        try {
            Path path = file.toPath();
            return Files.readAllBytes(path);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Collection<String> readLines(File file, String charset) {
        try {
            Path path = file.toPath();
            Charset cs = Charset.forName(charset);
            return Files.readAllLines(path, cs);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static class Builder implements EzyBuilder<EzyFileReader> {

        @Override
        public EzyFileReader build() {
            return new EzySimpleFileReader();
        }
    }
}
