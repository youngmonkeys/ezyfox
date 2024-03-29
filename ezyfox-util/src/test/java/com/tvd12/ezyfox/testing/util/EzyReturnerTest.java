package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyReturner;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EzyReturnerTest extends BaseTest {

    @Test
    public void test() {
        Asserts.assertEquals(EzyReturner.returnNotNull(10, 100), 10);
        Asserts.assertEquals(EzyReturner.returnNotNull(null, 100), 100);
    }

    @Test
    public void test2() {
        assert EzyReturner.returnWithException(() -> 100) == 100;
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void test3() {
        EzyReturner.returnWithException(() -> {
            throw new Exception();
        });
    }

    @Test
    public void test4() {
        assert EzyReturner.returnWithIllegalArgumentException(() -> 100) == 100;
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test5() {
        EzyReturner.returnWithIllegalArgumentException(() -> {
            throw new Exception();
        });
    }

    @Test
    public void test6() {
        Lock lock = new ReentrantLock();
        assert EzyReturner.returnWithLock(() -> 10, lock) == 10;
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void test7() {
        Lock lock = new ReentrantLock();
        EzyReturner.returnWithLock(() -> {
            throw new IllegalStateException();
        }, lock);
    }

    @Test
    public void test9() {
        assert EzyReturner.returnWithSync(() -> 10L, this) == 10L;
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void test10() {
        EzyReturner.returnWithSync(() -> {throw new IllegalStateException();}, this);
    }

    @Override
    public Class<?> getTestClass() {
        return EzyReturner.class;
    }
}
