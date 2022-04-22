package com.tvd12.ezyfox.testing.concurrent;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.concurrent.EzyFuture;
import com.tvd12.ezyfox.concurrent.EzyFutureMap;
import com.tvd12.ezyfox.concurrent.EzyFutureSynchronizedHashMap;
import com.tvd12.ezyfox.concurrent.exception.EzyFutureExistedException;

public class EzyFutureSynchronizeMap2Test {

    @Test
    public void test() {
        EzyFutureMap<String> map = new EzyFutureSynchronizedHashMap<>();
        EzyFuture future = map.addFuture("1");
        assert future != null;
        EzyFuture future2 = map.addFuture("1");
        assert future2 == future;
        assert map.size() == 1;
        assert map.removeFuture("1") == future;
        assert map.size() == 0;
    }

    @Test(expectedExceptions = EzyFutureExistedException.class)
    public void test2() {
        EzyFutureMap<String> map = new EzyFutureSynchronizedHashMap<>();
        EzyFuture future = map.putFuture("1");
        assert future != null;
        map.putFuture("1");
    }
}