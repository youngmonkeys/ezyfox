package com.tvd12.ezyfox.testing.pattern;

import com.tvd12.ezyfox.pattern.EzyObjectFactory;
import com.tvd12.ezyfox.pattern.EzyObjectPool;

public class MyTestObjectPool2 extends EzyObjectPool<MyTestObject> {

    protected MyTestObjectPool2(Builder builder) {
        super(builder);
    }

    public MyTestObject borrowOne() {
        return borrowObject();
    }

    public void returnOne(MyTestObject object) {
        returnObject(object);
    }

    @Override
    protected boolean isStaleObject(MyTestObject object) {
        return object.getId() % 2 == 0;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EzyObjectPool.Builder<MyTestObject, Builder> {

        @Override
        public MyTestObjectPool2 build() {
            return new MyTestObjectPool2(this);
        }

        @Override
        protected String getValidationThreadPoolName() {
            return "test-object";
        }

        @Override
        protected EzyObjectFactory<MyTestObject> newObjectFactory() {
            return new EzyObjectFactory<MyTestObject>() {
                @Override
                public MyTestObject newProduct() {
                    return new MyTestObject();
                }
            };
        }

    }
}
