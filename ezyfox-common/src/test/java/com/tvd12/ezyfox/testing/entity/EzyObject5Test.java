package com.tvd12.ezyfox.testing.entity;

import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.testing.CommonBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class EzyObject5Test extends CommonBaseTest {

    @Test
    public void test1() {
        EzyObject object = newObjectBuilder()
            .append("1", ABC.A)
            .build();
        assertEquals(object.get("1", ABC.class), ABC.A);
    }

    public enum ABC {
        A, B, C
    }
}
