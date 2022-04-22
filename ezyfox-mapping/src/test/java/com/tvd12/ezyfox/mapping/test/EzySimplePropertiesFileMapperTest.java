package com.tvd12.ezyfox.mapping.test;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.annotation.EzyProperty;
import com.tvd12.ezyfox.mapping.properties.EzyPropertiesFileMapper;
import com.tvd12.ezyfox.mapping.properties.EzySimplePropertiesFileMapper;

import lombok.Getter;
import lombok.Setter;

public class EzySimplePropertiesFileMapperTest {

    @Test
    public void test() {
        EzyPropertiesFileMapper mapper = EzySimplePropertiesFileMapper.builder()
                .context(getClass())
                .build();
        System.out.println(mapper.read("test-data/person.properties", Person.class));
    }

    @Getter
    @Setter
    public static class Person {
        @EzyProperty("nick_name")
        protected String nickName;
        @EzyProperty(prefix = "name")
        protected Name name;
    }

    public static class Name {
        protected String first;
        protected String last;
    }

}
