package com.tvd12.ezyfox.database.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.database.service.EzySaveManyService;

public class EzySaveManyServiceTest {

    @Test
    public void test() {
        new EzySaveManyService<Object>() {
            public void save(java.lang.Iterable<Object> entities) {};
        }
        .save(new Object(), new Object());
    }
}