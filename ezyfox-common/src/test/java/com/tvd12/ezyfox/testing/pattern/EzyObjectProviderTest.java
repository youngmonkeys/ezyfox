package com.tvd12.ezyfox.testing.pattern;

import java.util.List;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.concurrent.EzyExecutors;
import com.tvd12.ezyfox.exception.EzyNotImplementedException;
import com.tvd12.ezyfox.pattern.EzyObjectFactory;
import com.tvd12.ezyfox.pattern.EzyObjectProvider;

public class EzyObjectProviderTest {

    @Test
    public void test() throws Exception {
        TestObjectProvider provider = TestObjectProvider.builder()
                .validationDelay(100)
                .validationInterval(100)
                .objectFactory(new StringFactory())
                .build();
        provider.start();
        Thread.sleep(500);
        provider.newString();
        Thread.sleep(500);
        provider.getAddedStrings();
        provider.destroy();

    }

    @Test
    public void test1() throws Exception {
        new TestObjectProvider.Builder2()
            .validationInterval(100)
            .validationService(EzyExecutors.newScheduledThreadPool(1, "test"))
            .build();
    }

    @Test(expectedExceptions = {EzyNotImplementedException.class})
    public void test2() throws Exception {
        TestObjectProvider.builder()
            .validationInterval(100)
            .validationService(EzyExecutors.newScheduledThreadPool(1, "test"))
            .build();
    }

    @Test
    public void test3() throws Exception {
        TestObjectProvider provider = new TestObjectProvider2.Builder()
                .validationDelay(100)
                .validationInterval(100)
                .objectFactory(new StringFactory())
                .build();
        provider.start();
        Thread.sleep(500);
        try {
            provider.newString();
        }
        catch(Exception e) {
        }
        provider.newString();
        Thread.sleep(500);
        provider.getAddedStrings();
        provider.destroy();

    }

    private static class StringFactory implements EzyObjectFactory<String> {

        @Override
        public String newProduct() {
            return "default";
        }

    }

    private static class TestObjectProvider2 extends TestObjectProvider {

        private volatile boolean createObjectPassed = false;
        private volatile boolean clearAllPassed = false;
        private volatile boolean removeStaleObjectsPassed = false;

        public TestObjectProvider2(Builder builder) {
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
                throw new IllegalStateException();
            }
            return super.createObject();
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder extends TestObjectProvider.Builder {
            @Override
            public TestObjectProvider build() {
                return new TestObjectProvider2(this);
            }
        }

    }

    private static class TestObjectProvider extends EzyObjectProvider<String> {

        public TestObjectProvider(Builder builder) {
            super(builder);
        }

        public String newString() {
            return provideObject();
        }

        public List<String> getAddedStrings() {
            return getProvidedObjects();
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder extends EzyObjectProvider.Builder<String, Builder> {

            @Override
            public TestObjectProvider build() {
                return new TestObjectProvider(this);
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
