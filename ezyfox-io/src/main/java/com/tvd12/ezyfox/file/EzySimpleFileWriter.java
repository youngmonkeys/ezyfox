package com.tvd12.ezyfox.file;

import com.tvd12.ezyfox.builder.EzyBuilder;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class EzySimpleFileWriter implements EzyFileWriter {

    @Override
    public void write(File file, byte[] data) {
        try {
            Path path = file.toPath();
            Files.write(path, data);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void write(File file, InputStream stream) {
        try {
            Path target = file.toPath();
            Files.copy(stream, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void write(File file, CharSequence data, Charset charset) {
        try {
            Path path = file.toPath();
            byte[] bytes = data.toString().getBytes(charset);
            Files.write(path, bytes);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void write(File file, CharSequence data, String charset) {
        write(file, data, Charset.forName(charset));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements EzyBuilder<EzyFileWriter> {

        @Override
        public EzyFileWriter build() {
            return new EzySimpleFileWriter();
        }
    }
}
