package com.tvd12.ezyfox.database.testing;

import com.tvd12.ezyfox.database.service.EzySaveManyService;
import org.testng.annotations.Test;

public class EzySaveManyServiceTest {

    @Test
    public void test() {
        ((EzySaveManyService<Object>) entities -> {})
            .save(new Object(), new Object());
    }
}
