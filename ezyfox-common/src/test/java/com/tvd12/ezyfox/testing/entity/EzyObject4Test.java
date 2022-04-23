package com.tvd12.ezyfox.testing.entity;

import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.testing.CommonBaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class EzyObject4Test extends CommonBaseTest {

    @Test
    public void test1() {
        EzyObject object1 = newObjectBuilder().append("1", "a").build();
        EzyObject object2 = newObjectBuilder().append("2", "b").build();
        EzyObject object = newObjectBuilder()
            .append("1", new EzyObject[][]{{object1}, {object2}})
            .build();
        EzyObject[][] itemss = object.get("1", EzyObject[][].class);
        assertEquals(itemss[0][0].get("1", String.class), "a");
        assertEquals(itemss[1][0].get("2", String.class), "b");
    }
}
