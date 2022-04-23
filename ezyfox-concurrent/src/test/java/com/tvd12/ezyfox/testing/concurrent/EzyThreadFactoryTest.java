package com.tvd12.ezyfox.testing.concurrent;

import com.tvd12.ezyfox.concurrent.EzyThreadFactory;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class EzyThreadFactoryTest extends BaseTest {

    @Test
    public void test() {
        EzyThreadFactory factory = newFactoryBuilder().build();
        Thread thread = factory.newThread(() -> {});
        assertTrue(thread.getName().startsWith("ezyfox-test"));
        assertFalse(thread.isDaemon());

        factory = newFactoryBuilder().prefix(null).build();
        thread = factory.newThread(() -> {});
        assertTrue(thread.getName().startsWith("test"));

        factory = newFactoryBuilder().daemon(true).poolName((String) null).build();
        thread = factory.newThread(() -> {});
        assertTrue(thread.isDaemon());
        factory = newFactoryBuilder().priority(Thread.MAX_PRIORITY).build();
        thread = factory.newThread(() -> {});
        assertEquals(thread.getPriority(), Thread.MAX_PRIORITY);

    }

    @Test
    public void test1() {
        new Builder1();
        new Builder2();
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test2() {
        new Builder1().priority(-100000);
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test3() {
        new Builder1().priority(100000);
    }

    @Test
    public void test4() {
        EzyThreadFactory factory = new Builder3().build();
        factory.newThread(() -> {});

    }

    @Test
    public void test5() {
        EzyThreadFactory factory = new Builder4().build();
        factory.newThread(() -> {});

    }

    @Test
    public void test6() {
        EzyThreadFactory factory = new Builder4().daemon(true).build();
        factory.newThread(() -> {});

    }

    protected EzyThreadFactory.Builder newFactoryBuilder() {
        return EzyThreadFactory.builder()
            .daemon(false)
            .poolName(EzyThreadFactoryTest.class)
            .poolName("test")
            .prefix("ezyfox")
            .priority(Thread.NORM_PRIORITY)
            .threadGroup(Thread.currentThread().getThreadGroup());
    }

    public static class Builder1 extends EzyThreadFactory.Builder {
        @Override
        protected SecurityManager getSecurityManager() {
            return null;
        }
    }

    public static class Builder2 extends EzyThreadFactory.Builder {
        @Override
        protected SecurityManager getSecurityManager() {
            return new SecurityManager();
        }
    }

    public static class Builder3 extends EzyThreadFactory.Builder {
        @Override
        public EzyThreadFactory build() {
            return new EzyThreadFactory(this) {
                @Override
                protected void trySetUpThread(Thread thread) {
                    throw new RuntimeException();
                }
            };
        }
    }

    public static class Builder4 extends EzyThreadFactory.Builder {
        @Override
        public EzyThreadFactory build() {
            return new EzyThreadFactory(this) {
                @Override
                protected Thread createThread(Runnable runnable, String name) {
                    Thread thread = new Thread(runnable, name);
                    thread.setDaemon(true);
                    return thread;
                }
            };
        }
    }
}
