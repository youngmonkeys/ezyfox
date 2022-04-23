package com.tvd12.ezyfox.testing.util;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.util.EzyLinkedListSet;

public class EzyLinkedListSetTest {

    @Test
    public void test() {
        EzyLinkedListSet<String> set = new EzyLinkedListSet<>();
        assert set.add("1");
        assert !set.add("1");
        set.add(1, "2");
        set.add(1, "2");
        assert set.remove("1");
        assert set.size() == 1;
        assert set.remove().equals("2");
        assert set.add("1");
        assert set.add("2");
        assert set.removeAll(Sets.newHashSet("1"));
        assert set.add("1");
        assert set.add("3");
        assert set.size() == 3;
        assert set.retainAll(Sets.newHashSet("1"));
        assert set.size() == 1;
        assert set.remove(0).equals("1");
        assert set.isEmpty();
        assert set.add("1");
        assert set.removeFirst().equals("1");
        assert set.add("1");
        assert set.add("2");
        assert set.add("3");
        assert set.removeFirstOccurrence("3");
        assert set.removeIf(i -> i.equals("2"));
        assert set.size() == 1;
        assert set.removeLast().equals("1");
        assert set.size() == 0;
        assert set.add("1");
        assert set.add("2");
        assert set.add("3");
        assert set.removeLastOccurrence("3");
        assert set.addAll(Sets.newHashSet("3"));
        assert !set.addAll(Sets.newHashSet("3"));
        assert set.addAll(3, Lists.newArrayList("4", "5", "5"));
        set.addFirst("0");
        set.addFirst("0");
        assert set.size() == 6;
        set.addLast("6");
        set.addLast("6");
        assert set.offer("7");
        assert !set.offer("7");
        assert set.offerFirst("-1");
        assert !set.offerFirst("-1");
        assert set.offerLast("8");
        assert !set.offerLast("8");
        assert set.size() == 10;
        assert set.poll().equals("-1");
        assert set.poll().equals("0");
        assert set.pollFirst().equals("1");
        assert set.pollFirst().equals("2");
        assert set.pollLast().equals("8");
        assert set.pollLast().equals("7");
        set.push("x");
        set.pop().equals("x");
        assert set.add("a");
        assert set.add("b");
        assert set.add("c");
        assert set.containsAll(Sets.newHashSet("a", "b"));
        set.retainAll(Lists.newArrayList("a", "z", "a"));
        while(set.size() > 0)
            set.poll();
        assert set.poll() == null;
        assert set.pollFirst() == null;
        assert set.pollLast() == null;

    }
}
