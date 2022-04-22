package com.tvd12.ezyfox.testing.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.concurrent.EzyFuture;
import com.tvd12.ezyfox.concurrent.EzyFutureMap;
import com.tvd12.ezyfox.concurrent.EzyFutureSynchronizedHashMap;

public class EzyFutureSynchronizedHashMapTest {

    @Test
    public void test() throws Exception {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        EzyFutureMap<String> tasks = new EzyFutureSynchronizedHashMap<>();
        HandlingLoop loop = new HandlingLoop(queue, tasks);
        loop.start();
        int count = 10;
        EzyFuture[] futures = new EzyFuture[count];
        for(int i = 0 ; i < count ; ++i)
            futures[i] = tasks.addFuture(String.valueOf(i + 1));
        new Thread(() -> {
            for(int i = 0 ; i < count ; ++i) {
                try {
                    String answer = futures[i].get(1000);
                    System.out.println("answer: " + answer);
                }
                catch (Exception e) {
                    System.out.println("error: " + e.getMessage());
                }
                finally {
                    assert futures[i].isDone();
                }
            }
        }).start();
        Thread.sleep(1000);
        for(int i = 0 ; i < count ; ++i)
            queue.add(String.valueOf(i + 1));
        while(loop.count.get() < 9)
            Thread.sleep(1);
        loop.stop();
     }

    @Test
    public void clearCaseTest() {
        EzyFutureMap<String> tasks = new EzyFutureSynchronizedHashMap<>();
        tasks.addFuture("a");
        assert tasks.clear().size() == 1;
    }

    public static class HandlingLoop {

        protected volatile boolean active;
        protected EzyFutureMap<String> tasks;
        protected BlockingQueue<String> queue;
        protected AtomicInteger count = new AtomicInteger();

        public HandlingLoop(
                BlockingQueue<String> queue,
                EzyFutureMap<String> tasks) {
            this.queue = queue;
            this.tasks = tasks;
        }

        public void start() {
            Thread thread = new Thread(() -> loop());
            active = true;
            thread.start();
        }

        protected void loop() {
            try {
                while(active) {
                    String request = queue.take();
                    EzyFuture future = tasks.getFuture(request);
                    int c = count.incrementAndGet();
                    if(c % 2 == 0)
                        future.setResult("result of request: " + request);
                    else
                        future.setException(new Exception("exception of: " + request));
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected void stop() {
            this.active = false;
        }

    }
}