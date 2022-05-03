package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.constant.EzyLogLevel;
import com.tvd12.ezyfox.util.EzyProcessor;
import com.tvd12.test.base.BaseTest;
import com.tvd12.test.reflect.FieldUtil;
import org.slf4j.Logger;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.mockito.Mockito.*;

public class EzyProcessorTest extends BaseTest {

    @Test
    public void test() {
        EzyProcessor.processWithException(() -> {});
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void test1() {
        EzyProcessor.processWithException(() -> {
            throw new Exception();
        });
    }

    @Test
    public void test2() {
        EzyProcessor.processWithIllegalArgumentException(() -> {
        });
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test4() {
        EzyProcessor.processWithIllegalArgumentException(() -> {
            throw new Exception();
        });
    }


    @Test
    public void test5() {
        Lock lock = new ReentrantLock();
        EzyProcessor.processWithLock(() -> {}, lock);
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void test6() {
        Lock lock = new ReentrantLock();
        EzyProcessor.processWithLock(() -> {throw new IllegalStateException();}, lock);
    }

    @Test
    public void test7() {
        Lock lock = new ReentrantLock();
        try {
            EzyProcessor.processWithTryLock(() -> {}, lock, 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void test8() {
        Lock lock = new ReentrantLock();
        try {
            EzyProcessor.processWithTryLock(() -> {throw new IllegalStateException();}, lock, 10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test9() {
        Lock lock = new ReentrantLock() {
            private static final long serialVersionUID = 616957346213118575L;

            public boolean tryLock(long time, TimeUnit timeUnit) throws InterruptedException {
                throw new InterruptedException();
            }
        };
        try {
            EzyProcessor.processWithTryLock(() -> {
            }, lock, 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test10() {
        Lock lock = new ReentrantLock() {
            private static final long serialVersionUID = 616957346213118575L;

            public boolean tryLock(long time, TimeUnit timeUnit) {
                return false;
            }
        };
        try {
            EzyProcessor.processWithTryLock(() -> {
            }, lock, 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test11() {
        Lock lock = new ReentrantLock() {
            private static final long serialVersionUID = 616957346213118575L;

            public boolean tryLock() {
                return false;
            }
        };
        EzyProcessor.processWithTryLock(() -> {
        }, lock);
    }

    @Test
    public void test12() {
        Lock lock = new ReentrantLock();
        EzyProcessor.processWithTryLock(() -> {
        }, lock);
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void test13() {
        Lock lock = new ReentrantLock();
        EzyProcessor.processWithTryLock(() -> {
            throw new IllegalStateException();
        }, lock);
    }

    @Test
    public void test14() {
        EzyProcessor.processWithLogException(() -> {});
    }

    @Test
    public void test15() {
        EzyProcessor.processWithLogException(() -> {throw new Exception();});
    }

    @Test
    public void test16() {
        EzyProcessor.processWithSync(() -> {}, this);
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void test17() {
        EzyProcessor.processWithSync(() -> {throw new IllegalStateException();}, this);
    }

    @Test
    public void processWithLogWarn() {
        EzyProcessor.processWithLogException(() -> {throw new Exception();}, true);
    }

    @Test
    public void processSilentlyTest() {
        EzyProcessor.processSilently(() -> {});
        EzyProcessor.processSilently(() -> {throw new Exception("just test");});
    }

    @Test
    public void processWithTraceLog() {
        // given
        Logger logger = mock(Logger.class);
        FieldUtil.setStaticFieldValue(EzyProcessor.class, "LOGGER", logger);

        // when
        Exception e = new IllegalStateException("just test");
        EzyProcessor.processWithLogException(
            () -> { throw e; },
            EzyLogLevel.TRACE
        );

        // then
        verify(logger, times(1)).trace(any(String.class), any(Exception.class));
    }

    @Test
    public void processWithDebugLog() {
        // given
        Logger logger = mock(Logger.class);
        FieldUtil.setStaticFieldValue(EzyProcessor.class, "LOGGER", logger);

        // when
        Exception e = new IllegalStateException("just test");
        EzyProcessor.processWithLogException(
            () -> { throw e; },
            EzyLogLevel.DEBUG
        );

        // then
        verify(logger, times(1)).debug(any(String.class), any(Exception.class));
    }

    @Test
    public void processWithInfoLog() {
        // given
        Logger logger = mock(Logger.class);
        FieldUtil.setStaticFieldValue(EzyProcessor.class, "LOGGER", logger);

        // when
        Exception e = new IllegalStateException("just test");
        EzyProcessor.processWithLogException(
            () -> { throw e; },
            EzyLogLevel.INFO
        );

        // then
        verify(logger, times(1)).info(any(String.class), any(Exception.class));
    }

    @Test
    public void processWithWarnLog() {
        // given
        Logger logger = mock(Logger.class);
        FieldUtil.setStaticFieldValue(EzyProcessor.class, "LOGGER", logger);

        // when
        Exception e = new IllegalStateException("just test");
        EzyProcessor.processWithLogException(
            () -> { throw e; },
            EzyLogLevel.WARN
        );

        // then
        verify(logger, times(1)).warn(any(String.class), any(Exception.class));
    }

    @Test
    public void processWithErrorLog() {
        // given
        Logger logger = mock(Logger.class);
        FieldUtil.setStaticFieldValue(EzyProcessor.class, "LOGGER", logger);

        // when
        Exception e = new IllegalStateException("just test");
        EzyProcessor.processWithLogException(
            () -> { throw e; },
            EzyLogLevel.ERROR
        );

        // then
        verify(logger, times(1)).error(any(String.class), any(Exception.class));
    }

    @Test
    public void processWithFatalLog() {
        // given
        Logger logger = mock(Logger.class);
        FieldUtil.setStaticFieldValue(EzyProcessor.class, "LOGGER", logger);

        // when
        Exception e = new IllegalStateException("just test");
        EzyProcessor.processWithLogException(
            () -> { throw e; },
            EzyLogLevel.FATAL
        );

        // then
        verify(logger, times(1)).error(any(String.class), any(Exception.class));
    }

    @Test
    public void processWithWarnLogTrue() {
        // given
        Logger logger = mock(Logger.class);
        FieldUtil.setStaticFieldValue(EzyProcessor.class, "LOGGER", logger);

        // when
        Exception e = new IllegalStateException("just test");
        EzyProcessor.processWithLogException(
            () -> { throw e; },
            true
        );

        // then
        verify(logger, times(1)).warn(any(String.class), any(Exception.class));
    }

    @Test
    public void processWithWarnLogFalse() {
        // given
        Logger logger = mock(Logger.class);
        FieldUtil.setStaticFieldValue(EzyProcessor.class, "LOGGER", logger);

        // when
        Exception e = new IllegalStateException("just test");
        EzyProcessor.processWithLogException(
            () -> { throw e; },
            false
        );

        // then
        verify(logger, times(1)).info(any(String.class), any(Exception.class));
    }

    @Override
    public Class<?> getTestClass() {
        return EzyProcessor.class;
    }
}
