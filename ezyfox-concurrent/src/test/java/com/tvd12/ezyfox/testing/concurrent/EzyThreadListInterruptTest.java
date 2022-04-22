package com.tvd12.ezyfox.testing.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.tvd12.ezyfox.concurrent.EzyThreadList;

public class EzyThreadListInterruptTest {

    public static void main(String[] args) throws Exception {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        EzyThreadList threadList = new EzyThreadList(3, () -> {
            while(true) {
                try {
                    String str = queue.take();
                    System.out.print("str: " + str);
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }, "test");
        threadList.execute();
        Thread.sleep(300);
        queue.add("Hello");
        Thread.sleep(300);
        threadList.interrupt();
    }
}