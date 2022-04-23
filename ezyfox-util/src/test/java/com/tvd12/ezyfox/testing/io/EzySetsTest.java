package com.tvd12.ezyfox.testing.io;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.io.EzySets;
import com.tvd12.test.base.BaseTest;
import com.tvd12.test.performance.Performance;

public class EzySetsTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzySets.class;
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test() {
        Set<String> set = EzySets.combine(Sets.newHashSet("1", "2", "3"),
                Sets.newHashSet("4", "5", "6"));
        assertEquals(set, Sets.newHashSet("1", "2", "3", "4", "5", "6"));

        Collection<String> coll1 = Lists.newArrayList("ab", "cde");
        Set<Character> set1 = EzySets.newHashSetByAddAll(coll1, t -> this.stringtoChars(t));
        assertEquals(set1, Sets.newHashSet('a', 'b', 'c', 'd', 'e'));

        Set<String> set2 = EzySets.filter(set,
                (str) -> (!str.startsWith("1") && !str.startsWith("5")));
        assertEquals(set2, Sets.newHashSet("2", "3", "4", "6"));

        Set<String> set3 = EzySets.newHashSet(set, "3" ,"4");
        assertEquals(set3, Sets.newHashSet("1", "2", "5", "6"));

        Set<String> set4 = EzySets.newHashSet(new Long[] {1L, 2L, 3L}, v -> v.toString());
        assertEquals(set4, Sets.newHashSet("1", "2", "3"));

        Set<String> set5 = EzySets.newHashSet(Lists.newArrayList(1L, 2L, 3L), v -> v.toString());
        assertEquals(set5, Sets.newHashSet("1", "2", "3"));

        Map<String, Long> map = new HashMap<>();
        map.put("a", 1L);
        map.put("b", 2L);
        Set<String> set6 = EzySets.newHashSet(map, (k, v) -> v + k);
        assertEquals(set6, Sets.newHashSet("1a", "2b"));

        Set<String> set7 = EzySets.addElementsToNewSet(new HashSet<>(), "1", "2", "3");
        assertEquals(set7, Sets.newHashSet("1", "2", "3"));
    }

    @Test
    public void test2() {
        Set<Integer> set = Sets.newHashSet(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 28, 39, 30);
        Set<Integer> except = Sets.newHashSet(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        long time1 = Performance.create()
            .loop(1000)
            .test(() -> {
                EzySets.newHashSet(set, except);
            })
            .getTime();
        System.out.println("time1: " + time1);
        long time2 = Performance.create()
                .loop(1000)
                .test(() -> {
                    newHashSet2(set, except);
                })
                .getTime();
        System.out.println("time2: " + time2);
        Set<Integer> newSet = newHashSet2(set, except);
        assertEquals(newSet, Sets.newHashSet(11, 12, 13, 14, 15, 16, 17, 28, 39, 30));

        Set<Integer> newSet2 = EzySets.newHashSet(set, except);
        assertEquals(newSet2, Sets.newHashSet(11, 12, 13, 14, 15, 16, 17, 28, 39, 30));
    }

    private Collection<Character> stringtoChars(String str) {
        List<Character> answer = new ArrayList<>();
        for(int i = 0 ; i < str.length() ; ++i)
            answer.add(str.charAt(i));
        return answer;
    }

    public static <T> Set<T> newHashSet(Collection<T> coll, Collection<T> except) {
        Set<T> answer = new HashSet<>(coll);
        answer.removeAll(except);
        return answer;
    }

    public static <T> Set<T> newHashSet2(Collection<T> coll, Collection<T> except) {
        Set<T> answer = new HashSet<>();
        for(T item : coll) {
                if(!except.contains(item))
                    answer.add(item);
        }
        return answer;
    }
}
