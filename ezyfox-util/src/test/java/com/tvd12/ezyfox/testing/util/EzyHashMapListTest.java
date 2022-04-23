package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.util.EzyHashMapList;
import com.tvd12.ezyfox.util.EzyMapList;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class EzyHashMapListTest extends BaseTest {

    @Test
    public void test() {
        EzyMapList<String, String> map = new EzyHashMapList<>();
        map.addItems("1", "a", "b", "c");
        assertEquals(map.get("1"), Lists.newArrayList("a", "b", "c"));
        map.removeItems("1", "b", "c");
        assertEquals(map.get("1"), Lists.newArrayList("a"));
        map.removeItems("zzz");
        assert map.getItems("abc").size() == 0;
        map.addItems("a", Lists.newArrayList("a", "b", "c"));
        assert map.getItems("a").size() == 3;
        map.deepClear();
        assert map.size() == 0;
        map.addItem("a", "x");
        assert map.getItems("a").size() == 1;
        map.addItem("a", "y");
        assert map.getItems("a").size() == 2;
    }
}
