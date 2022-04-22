package com.tvd12.ezyfox.testing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tvd12.test.performance.Performance;

public class CopyOnWriteArrayListTest {

    public static void main(String[] args) {
        int size = 10000;
        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        for(int i = 0 ; i < size ; ++i) {
            set.add(String.valueOf(i));
        }
        System.out.println("start test1");
        long time1 = new Performance()
            .test(() -> set.remove("a"))
            .getTime();
        System.out.println("time1: " + time1);

        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for(int i = 0 ; i < size ; ++i) {
            list.add(String.valueOf(i));
        }
        System.out.println("start test2");
        long time2 = new Performance()
            .test(() -> list.remove("a"))
            .getTime();
        System.out.println("time2: " + time2);
    }
}