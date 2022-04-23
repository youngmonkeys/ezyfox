package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.util.EzyHashMapSet;
import com.tvd12.ezyfox.util.EzyMapSet;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.util.HashSet;

import static org.testng.Assert.assertEquals;

public class EzyHashMapSetTest extends BaseTest {

    @Test
    public void test() {
        EzyMapSet<String, String> map = new EzyHashMapSet<>();
        map.addItems("1", "a", "b", "c");
        assertEquals(map.get("1"), Sets.newHashSet("a", "b", "c"));
        map.removeItems("1", "b", "c");
        assertEquals(map.get("1"), Sets.newHashSet("a"));
        map.removeItems("zzz");
        map.addItems("abc", new HashSet<>());
        map.addItems("abc", new HashSet<>());
    }
}
