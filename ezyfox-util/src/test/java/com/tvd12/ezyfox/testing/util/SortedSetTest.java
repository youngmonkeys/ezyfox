package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.collect.Sets;

import java.util.HashSet;
import java.util.Set;

public class SortedSetTest {

    @SuppressWarnings("ALL")
    public static void main(String[] args) {
        int size = 1000000;
        Set<String> normalSet = new HashSet<>();
        for (int i = 0; i < size; ++i) {
            normalSet.add(i + "");
        }

        System.out.println("setup done");

        Set<String> find = Sets.newHashSet("100", "123", "345", "1", "10", "999999990000");
        long startTime1 = System.currentTimeMillis();
        for (int i = 0; i < 100000000; ++i) {
            normalSet.containsAll(find);
        }
        long endTime1 = System.currentTimeMillis();

        long startTime2 = System.currentTimeMillis();
        long endTime2 = System.currentTimeMillis();

        long offset1 = endTime1 - startTime1;
        long offset2 = endTime2 - startTime2;

        System.out.println("offset1 = " + offset1 + ", ofsset2 = " + offset2);
    }
}
