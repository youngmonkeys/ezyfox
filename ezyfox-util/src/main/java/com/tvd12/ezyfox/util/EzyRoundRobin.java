package com.tvd12.ezyfox.util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class EzyRoundRobin<T> {

    protected final Queue<T> queue;

    public EzyRoundRobin() {
        this.queue = new LinkedList<>();
    }

    public EzyRoundRobin(Supplier<T> supplier, int size) {
        this();
        for(int i = 0 ; i < size ; ++i)
            this.queue.offer(supplier.get());
    }

    public void add(T item) {
        synchronized (queue) {
            this.queue.add(item);
        }
    }

    public T get() {
        T last = null;
        synchronized (queue) {
            last = queue.poll();
            queue.offer(last);
        }
        return last;
    }

    public void forEach(Consumer<T> consumer) {
        synchronized (queue) {
            for(T item : queue)
                consumer.accept(item);
        }
    }

}
