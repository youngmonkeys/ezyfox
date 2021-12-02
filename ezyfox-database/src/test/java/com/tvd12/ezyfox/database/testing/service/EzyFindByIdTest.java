package com.tvd12.ezyfox.database.testing.service;

import java.util.Optional;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.database.service.EzyFindById;
import com.tvd12.test.assertion.Asserts;

public class EzyFindByIdTest {

    @Test
    public void findByFieldOptionalTest() {
        // given
        EzyFindById<String, String> sut = new EzyFindById<String, String>() {
            @Override
            public String findById(String id) {
                return "hello";
            }
        };
        
        // when
        Optional<String> actual = sut.findByIdOptional(null);
        
        // then
        Asserts.assertEquals(actual, Optional.of("hello"));
    }
}
