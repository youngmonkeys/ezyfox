package com.tvd12.ezyfox.mapping.test.jackson;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.mapping.jackson.EzySimpleJsonMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class EzySimpleJsonMapperTest {

    @Test
    public void test() {
        EzySimpleJsonMapper mapper = EzySimpleJsonMapper.builder()
                .build();
        System.out.println(mapper.writeAsString(new A("hello")));
        System.out.println(mapper.writeAsString(new B("world")));
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    public static class A  {
        private String name;
    }

    @Setter
    @ToString
    @AllArgsConstructor
    public static class B {
        protected String name;

        public String getName() {
            throw new RuntimeException();
        }
    }
}
