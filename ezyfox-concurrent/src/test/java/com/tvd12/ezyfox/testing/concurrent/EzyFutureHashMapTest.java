package com.tvd12.ezyfox.testing.concurrent;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.concurrent.EzyFuture;
import com.tvd12.ezyfox.concurrent.EzyFutureHashMap;
import com.tvd12.ezyfox.concurrent.EzyFutureMap;
import com.tvd12.ezyfox.concurrent.exception.EzyFutureExistedException;

public class EzyFutureHashMapTest {

    @Test
    public void test() {
        EzyFutureMap<String> map = new EzyFutureHashMap<>();
        EzyFuture future = map.addFuture("1");
        assert future != null;
        EzyFuture future2 = map.addFuture("1");
        assert future2 == future;
        assert map.size() == 1;
    }

    @Test(expectedExceptions = EzyFutureExistedException.class)
    public void test2() {
        EzyFutureMap<String> map = new EzyFutureHashMap<>();
        EzyFuture future = map.putFuture("1");
        assert future != null;
        map.putFuture("1");
    }

}
