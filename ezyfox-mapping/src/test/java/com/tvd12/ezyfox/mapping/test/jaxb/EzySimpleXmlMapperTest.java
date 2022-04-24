package com.tvd12.ezyfox.mapping.test.jaxb;

import com.tvd12.ezyfox.mapping.jaxb.EzySimpleXmlMapper;
import org.testng.annotations.Test;

public class EzySimpleXmlMapperTest {

    @Test
    public void test() {
        EzySimpleXmlMapper mapper = EzySimpleXmlMapper.builder()
            .contextClass(getClass())
            .contextPath(getClass().getPackage().getName())
            .classLoader(getClass().getClassLoader())
            .build();
        System.out.println(mapper.read("test-data/person.xml", Person.class));
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test2() {
        EzySimpleXmlMapper.builder()
            .contextPath("a b c")
            .classLoader(getClass().getClassLoader())
            .build();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void test3() {
        EzySimpleXmlMapper mapper = EzySimpleXmlMapper.builder()
            .contextClass(getClass())
            .contextPath(getClass().getPackage().getName())
            .classLoader(getClass().getClassLoader())
            .build();
        System.out.println(mapper.read("test-data/person-error.txt", Person.class));
    }
}
