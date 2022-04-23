package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyLinkedListSet;
import com.tvd12.test.performance.Performance;
import org.testng.annotations.Test;

import java.util.Queue;

public class QueuePerfomanceTest {

    @Test
    @SuppressWarnings("ALL")
    public void test() {
        Queue<String> players = new EzyLinkedListSet<>();
        for (int i = 0; i < 10000; ++i) {
            String player = new String("player#" + i);
            players.add(player);
        }
        long time1 = new Performance()
            .test(() -> {
                players.contains(new String("ply"));
            })
            .getTime();
        System.out.println(time1);
    }
}
