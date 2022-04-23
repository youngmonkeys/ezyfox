package com.tvd12.ezyfox.testing.pattern;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.concurrent.EzyExecutors;
import com.tvd12.ezyfox.exception.EzyNotImplementedException;
import com.tvd12.ezyfox.pattern.EzyObjectFactory;
import com.tvd12.ezyfox.pattern.EzyObjectPool;

public class EzyObjectPool6Test {

    @Test
    public void test() throws Exception {
        TestObjectPool provider = TestObjectPool.builder()
                .maxObjects(2)
                .validationDelay(100)
                .validationInterval(100)
                .objectFactory(new StringFactory())
                .build();
        provider.start();
        provider.newString();
        Thread.sleep(300);
        provider.newString();
        provider.newString();
        Thread.sleep(300);
        provider.destroy();

    }

    @Test
    public void test1() throws Exception {
        new TestObjectPool.Builder2()
            .validationInterval(100)
            .validationService(EzyExecutors.newScheduledThreadPool(1, "test"))
            .build();
    }

    @Test(expectedExceptions = {EzyNotImplementedException.class})
    public void test2() throws Exception {
        TestObjectPool.builder()
            .validationInterval(100)
            .validationService(EzyExecutors.newScheduledThreadPool(1, "test"))
            .build();
    }

    @Test
    public void test3() throws Exception {
        TestObjectPool provider = new TestObjectPool2.Builder()
                .validationDelay(100)
                .validationInterval(100)
                .objectFactory(new StringFactory())
                .build();
        provider.start();
        Thread.sleep(300);
        try {
            provider.newString();
        }
        catch(Exception e) {
        }
        provider.newString();
        Thread.sleep(300);
        provider.destroy();
    }

    @Test
    public void test4() throws Exception {
        TestObjectPool3 provider = TestObjectPool3.builder()
                .maxObjects(2)
                .validationDelay(100)
                .validationInterval(100)
                .objectFactory(new StringFactory())
                .build();
        provider.start();
        provider.newString();
        Thread.sleep(300);
        provider.newString();
        provider.newString();
        provider.returnString("default");
        provider.returnString(null);
        Thread.sleep(300);
        provider.destroy();

    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void test5() throws Exception {
        TestObjectPool3 provider = TestObjectPool3.builder()
                .maxObjects(2)
                .validationDelay(100)
                .validationInterval(100)
                .objectFactory(new StringFactory())
                .build();
        provider.getRemainString();
    }

    @Test
    public void test6() throws Exception {
        TestObjectPool3 provider = TestObjectPool3.builder()
                .maxObjects(2)
                .validationDelay(100)
                .validationInterval(100)
                .objectFactory(new StringFactory())
                .build();
        provider.start();
        Thread.sleep(300);
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void test7() throws Exception {
        TestObjectPool4 provider = TestObjectPool4.builder()
                .maxObjects(2)
                .validationDelay(100)
                .validationInterval(100)
                .objectFactory(new StringFactory())
                .build();
        provider.newString();
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void test8() throws Exception {
        TestObjectPool4 provider = TestObjectPool4.builder()
                .maxObjects(2)
                .validationDelay(100)
                .validationInterval(100)
                .objectFactory(new StringFactory())
                .build();
        provider.returnString("default");
    }

    private static class StringFactory implements EzyObjectFactory<String> {

        @Override
        public String newProduct() {
            return "default";
        }
    }

    private static class ConcurrentLinkedQueueEx2 extends ConcurrentLinkedQueue<String> {
        private static final long serialVersionUID = -6293369343194182159L;

        @Override
        public String poll() {
            throw new IllegalStateException();
        }

        @Override
        public boolean offer(String e) {
            throw new IllegalStateException();
        }
    }

    private static class ConcurrentLinkedQueueEx1 extends ConcurrentLinkedQueue<String> {
        private static final long serialVersionUID = -6293369343194182159L;

        protected boolean sizePassed = false;

        @Override
        public String[] toArray() {
            throw new IllegalStateException();
        }

        @Override
        public int size() {
            if(!sizePassed) {
                sizePassed = true;
                throw new IllegalStateException();
            }
            return super.size();
        }
    }

    private static class TestObjectPool4 extends EzyObjectPool<String> {

        protected TestObjectPool4(Builder builder) {
            super(builder);
        }

        @Override
        protected Queue<String> newObjectQueue() {
            return new ConcurrentLinkedQueueEx2();
        }

        @Override
        protected void initializeObjects() {
        }

        public String newString() {
            return borrowObject();
        }

        public boolean returnString(String str) {
            return returnObject(str);
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder extends EzyObjectPool.Builder<String, Builder> {

            @Override
            public TestObjectPool4 build() {
                return new TestObjectPool4(this);
            }

            @Override
            protected String getValidationThreadPoolName() {
                return "test";
            }

            @Override
            public EzyObjectFactory<String> getObjectFactory() {
                return super.getObjectFactory();
            }

            @Override
            public ScheduledExecutorService getValidationService() {
                return super.getValidationService();
            }
        }
    }

    private static class TestObjectPool3 extends EzyObjectPool<String> {

        protected TestObjectPool3(Builder builder) {
            super(builder);
        }

        @Override
        protected Queue<String> newObjectQueue() {
            return new ConcurrentLinkedQueueEx1();
        }

        public String newString() {
            return borrowObject();
        }

        public boolean returnString(String str) {
            return returnObject(str);
        }

        public List<String> getRemainString() {
            return getRemainObjects();
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder extends EzyObjectPool.Builder<String, Builder> {

            @Override
            public TestObjectPool3 build() {
                return new TestObjectPool3(this);
            }

            @Override
            protected String getValidationThreadPoolName() {
                return "test";
            }

            @Override
            public EzyObjectFactory<String> getObjectFactory() {
                return super.getObjectFactory();
            }

            @Override
            public ScheduledExecutorService getValidationService() {
                return super.getValidationService();
            }
        }
    }

    private static class TestObjectPool2 extends TestObjectPool {

        private volatile boolean createObjectPassed = false;
        private volatile boolean clearAllPassed = false;
        private volatile boolean removeStaleObjectsPassed = false;

        public TestObjectPool2(Builder builder) {
            super(builder);
        }

        @Override
        protected void removeStaleObjects(List<String> buffer) {
            if(!removeStaleObjectsPassed) {
                removeStaleObjectsPassed = true;
                throw new IllegalStateException();
            }
        }

        @Override
        protected void clearAll() {
            if(!clearAllPassed) {
                clearAllPassed = true;
                throw new IllegalStateException();
            }
        }

        @Override
        protected String createObject() {
            if(!createObjectPassed ) {
                createObjectPassed = true;
            }
            return super.createObject();
        }

        @Override
        protected boolean isStaleObject(String object) {
            return super.isStaleObject(object);
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder extends TestObjectPool.Builder {
            @Override
            public TestObjectPool build() {
                return new TestObjectPool2(this);
            }
        }

    }

    private static class TestObjectPool extends EzyObjectPool<String> {

        public TestObjectPool(Builder builder) {
            super(builder);
        }

        public String newString() {
            return borrowObject();
        }

        @Override
        protected boolean isStaleObject(String object) {
            return true;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder extends EzyObjectPool.Builder<String, Builder> {

            @Override
            public TestObjectPool build() {
                return new TestObjectPool(this);
            }

            @Override
            protected String getValidationThreadPoolName() {
                return "test";
            }

        }

        public static class Builder2 extends Builder {

            @Override
            protected EzyObjectFactory<String> newObjectFactory() {
                return new StringFactory();
            }
        }

    }
}
