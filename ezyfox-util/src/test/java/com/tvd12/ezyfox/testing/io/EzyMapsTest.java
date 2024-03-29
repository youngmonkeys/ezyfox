package com.tvd12.ezyfox.testing.io;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.io.EzyMaps;
import com.tvd12.ezyfox.util.EzyMapBuilder;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.util.*;

import static org.testng.Assert.assertEquals;

public class EzyMapsTest extends BaseTest {

    @Test
    public void test() {
        Map<Object, Object> map = new HashMap<>();
        ClassA classA = new ClassA();
        ClassD classD = new ClassD();
        ClassJ classJ = new ClassJ();
        map.put(InterfaceA.class, classA);
        map.put(ClassD.class, classD);
        map.put(ClassF.class, classJ);
        assert EzyMaps.getValue(map, ClassA.class) == classA;
        assert EzyMaps.getValue(map, ClassE.class) == classD;
        assert EzyMaps.getValue(map, ClassJ.class) == classJ;
        assert EzyMaps.isEmpty(null);
        assert EzyMaps.isEmpty(new HashMap<>());
        assert !EzyMaps.isEmpty(EzyMaps.newHashMap("a", "b"));
    }

    @Test
    public void test2() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        assertEquals(EzyMaps.getValueList(map), new ArrayList<>(map.values()));
        assertEquals(EzyMaps.getValueSet(map), new HashSet<>(map.values()));
    }

    @Test
    public void test3() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        Map<Long, String> map1 =
            EzyMaps.newHashMapNewKeys(map, Long::valueOf);
        assertEquals(map1.keySet(), Sets.newHashSet(1L, 2L, 3L));
        assertEquals(map1.values(), Sets.newHashSet("a", "b", "c"));
    }

    @Test
    public void test4() {
        Collection<String> coll = Sets.newHashSet("1", "2", "3");
        Map<Long, String> map = EzyMaps.newHashMap(coll, Long::valueOf);
        assertEquals(map.keySet(), Sets.newHashSet(1L, 2L, 3L));
        assertEquals(map.values(), Sets.newHashSet("1", "2", "3"));
    }

    @Test
    public void test5() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        Map<String, Character> map1 =
            EzyMaps.newHashMapNewValues(map, (v) -> v.charAt(0));
        assertEquals(map1.keySet(), Sets.newHashSet("1", "2", "3"));
        assertEquals(map1.values(), Sets.newHashSet('a', 'b', 'c'));
    }

    @Test
    public void test6() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        Map<String, String> map1 =
            EzyMaps.getValues(map, Lists.newArrayList("1", "2", "5"));
        assertEquals(map1.values(), Sets.newHashSet("a", "b"));
    }

    @Test
    public void test7() {
        Map<String, String> map = new HashMap<>();
        map.put("1", "a");
        map.put("2", "b");
        map.put("3", "c");
        List<String> list =
            EzyMaps.getValues(map, (v) -> !v.startsWith("b"));
        assertEquals(list, Lists.newArrayList("a", "c"));
    }

    @Test
    public void test8() {
        Map<String, List<String>> map = new HashMap<>();
        map.put("1", Lists.newArrayList("a", "b", "c", "d", "e"));
        assertEquals(map.get("1"), Lists.newArrayList("a", "b", "c", "d", "e"));
        EzyMaps.removeItems(map, "2", "d", "e");
        EzyMaps.removeItems(map, "1", "d", "e");
        assertEquals(map.get("1"), Lists.newArrayList("a", "b", "c"));
    }

    @Test
    public void test9() {
        Map<String, Set<String>> map = new HashMap<>();
        map.put("1", Sets.newHashSet("a", "b", "c", "d", "e"));
        assertEquals(map.get("1"), Sets.newHashSet("a", "b", "c", "d", "e"));
    }

    @Test
    public void test1() {
        assert EzyMaps.getValue(new HashMap<>(), Object.class) == null;
    }

    @Test
    public void test10() {
        Map<String, String> map = EzyMaps.newHashMap("a", "b");
        assert map.get("a").equals("b");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test11() {
        Map<String, String> map1 = EzyMapBuilder.mapBuilder()
            .put("1", "a")
            .put("2", "b")
            .put("3", "c")
            .build();
        Map<String, String> map2 = EzyMapBuilder.mapBuilder()
            .put("1", "a")
            .put("3", "c")
            .build();
        assert EzyMaps.containsAll(map1, map2);

        Map<String, String> map1a = EzyMapBuilder.mapBuilder()
            .put("1", "a")
            .put("2", "b")
            .put("3", "c")
            .build();
        Map<String, String> map2a = EzyMapBuilder.mapBuilder()
            .put("1", "a")
            .put("2", "f")
            .build();
        assert !EzyMaps.containsAll(map1a, map2a);

        Map<String, String> map1b = EzyMapBuilder.mapBuilder()
            .put("1", "a")
            .put("2", "b")
            .put("3", "c")
            .build();
        Map<String, String> map2b = EzyMapBuilder.mapBuilder()
            .put("x", "a")
            .put("y", "f")
            .build();
        assert !EzyMaps.containsAll(map1b, map2b);
    }

    @Test
    public void test13() {

        Map<String, List<String>> map3 = new HashMap<>();
        map3.put("1", new ArrayList<>());
        map3.put("2", new ArrayList<>());

        EzyMaps.removeItems(map3, "1", new ArrayList<>());
        map3.remove("1");
        EzyMaps.removeItems(map3, "1", new ArrayList<>());
    }

    @Test
    public void flattenValuesTest() {
        // given
        Map<String, List<String>> map = EzyMapBuilder.mapBuilder()
            .put("a", Arrays.asList("a", "b"))
            .put("b", Arrays.asList("c", "d"))
            .put("c", Arrays.asList("e", "f"))
            .toMap();

        // when
        List<String> actual = EzyMaps.flattenValues(map);

        // then
        List<String> expectation = Arrays.asList("a", "b", "c", "d", "e", "f");
        Asserts.assertEquals(actual, expectation, false);
    }

    @Override
    public Class<?> getTestClass() {
        return EzyMaps.class;
    }

    public interface InterfaceA {}

    public interface InterfaceB {}

    public static class ClassA implements InterfaceA {}

    public static class ClassB extends ClassA {}

    public static class ClassD {}

    public static class ClassE extends ClassD {}

    public static class ClassF {}

    public static class ClassJ extends ClassF implements InterfaceB {}
}
