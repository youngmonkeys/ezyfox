package com.tvd12.ezyfox.stream;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.exception.EzyFileNotFoundException;
import com.tvd12.ezyfox.file.EzyFileFetcher;
import com.tvd12.ezyfox.file.EzySimpleFileFetcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EzySimpleInputStreamLoader implements EzyInputStreamLoader {

    protected final EzyFileFetcher fileFetcher;

    public EzySimpleInputStreamLoader() {
        this(builder());
    }

    protected EzySimpleInputStreamLoader(Builder builder) {
        this.fileFetcher = builder.newFileFetcher();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public InputStream load(String filePath) {
        try {
            File file = getFile(filePath);
            return file != null ? new FileInputStream(file) : null;
        } catch (Exception e) {
            throw processException(e);
        }
    }

    protected RuntimeException processException(Exception e) {
        if (e instanceof EzyFileNotFoundException) {
            return (EzyFileNotFoundException) e;
        }
        if (e instanceof FileNotFoundException) {
            return new EzyFileNotFoundException(e);
        }
        throw new IllegalArgumentException(e);
    }

    protected File getFile(String filePath) throws FileNotFoundException {
        return fileFetcher.get(filePath);
    }

    public static class Builder
        implements EzyBuilder<EzyInputStreamLoader> {

        protected boolean throwException = true;

        public Builder throwException(boolean throwException) {
            this.throwException = throwException;
            return this;
        }

        @Override
        public EzyInputStreamLoader build() {
            return new EzySimpleInputStreamLoader(this);
        }

        protected EzyFileFetcher newFileFetcher() {
            return EzySimpleFileFetcher.builder()
                .throwException(throwException)
                .build();
        }
    }
}
