package com.tvd12.ezyfox.mapping.properties;

import com.tvd12.ezyfox.annotation.EzyProperty;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.io.EzySimpleValueConverter;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.properties.file.annotation.PropertyAnnotation;
import com.tvd12.properties.file.io.ValueConverter;
import com.tvd12.properties.file.mapping.PropertiesMapper;

public class EzySimplePropertiesFileMapper 
        extends EzyLoggable
        implements EzyPropertiesFileMapper {

    protected final ClassLoader classLoader;

    protected EzySimplePropertiesFileMapper(Builder builder) {
        this.classLoader = builder.classLoader;
    }

    @Override
    public <T> T read(String filePath, Class<T> valueType) {
        return new PropertiesMapper()
                .classLoader(classLoader)
                .addPropertyAnnotation(new PropertyAnnotation(
                        EzyProperty.class,
                        a -> ((EzyProperty)a).value(),
                        a -> ((EzyProperty)a).prefix()))
                .valueConverter(new ValueConverter() {
                    @Override
                    public <R> R convert(Object value, Class<R> outType) {
                        return EzySimpleValueConverter
                                .getSingleton()
                                .convert(value, outType);
                    }
                })
                .file(filePath)
                .map(valueType);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder implements EzyBuilder<EzyPropertiesFileMapper> {

        protected ClassLoader classLoader;

        @SuppressWarnings("rawtypes")
        public Builder context(Class context) {
            return classLoader(context.getClassLoader());
        }

        public Builder classLoader(ClassLoader classLoader) {
            this.classLoader = classLoader;
            return this;
        }

        @Override
        public EzyPropertiesFileMapper build() {
            return new EzySimplePropertiesFileMapper(this);
        }
    }

}
