package com.tvd12.ezyfox.stream;

import java.io.InputStream;

import com.tvd12.ezyfox.builder.EzyBuilder;

public class EzyClassPathInputStreamLoader implements EzyInputStreamLoader {

    protected final ClassLoader classLoader;

    public EzyClassPathInputStreamLoader() {
        this(builder());
    }

    protected EzyClassPathInputStreamLoader(Builder builder) {
        this.classLoader = builder.classLoader;
    }

    @Override
    public InputStream load(String filePath) {
        InputStream stream = firstLoad(filePath);
        return stream != null ? stream : secondLoad(filePath);
    }

    protected InputStream firstLoad(String filePath) {
        return classLoader.getResourceAsStream(filePath);
    }

    protected InputStream secondLoad(String filePath) {
        return firstLoad("/" + filePath);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements EzyBuilder<EzyInputStreamLoader> {

        protected ClassLoader classLoader = getClass().getClassLoader();

        public Builder context(Class<?> context) {
            return classLoader(context.getClassLoader());
        }

        public Builder classLoader(ClassLoader classLoader) {
            this.classLoader = classLoader;
            return this;
        }

        @Override
        public EzyInputStreamLoader build() {
            return new EzyClassPathInputStreamLoader(this);
        }
    }
}
