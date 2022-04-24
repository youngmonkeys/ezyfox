package com.tvd12.ezyfox.mapping.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class EzySimpleXmlMapper implements EzyXmlMapper {

    private final Unmarshaller unmarshaller;

    private EzySimpleXmlMapper(Builder builder) {
        this.unmarshaller = builder.newUnmarshaller();
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public <T> T read(File xmlFile, Class<T> outputType) {
        try {
            return unmarshaller.unmarshal(new StreamSource(xmlFile), outputType).getValue();
        } catch (JAXBException e) {
            throw new IllegalArgumentException(
                "Can not read xml file "
                    + xmlFile.getAbsolutePath() + " with " + outputType,
                e
            );
        }
    }

    public static class Builder {
        private String contextPath;
        private ClassLoader classLoader;

        public Builder contextClass(Class<?> clazz) {
            this.classLoader = clazz.getClassLoader();
            this.contextPath = clazz.getPackage().getName();
            return this;
        }

        public Builder contextPath(String contextPath) {
            this.contextPath = contextPath;
            return this;
        }

        public Builder classLoader(ClassLoader classLoader) {
            this.classLoader = classLoader;
            return this;
        }

        protected Unmarshaller newUnmarshaller() {
            try {
                JAXBContext context = newJAXBContext();
                return context.createUnmarshaller();
            } catch (JAXBException e) {
                throw new IllegalArgumentException(e);
            }
        }

        public EzySimpleXmlMapper build() {
            return new EzySimpleXmlMapper(this);
        }

        @SuppressWarnings("AbbreviationAsWordInName")
        protected JAXBContext newJAXBContext() throws JAXBException {
            return JAXBContext.newInstance(contextPath, classLoader);
        }
    }
}
