package com.tvd12.ezyfox.testing.pattern;

import java.util.List;

import com.tvd12.ezyfox.pattern.EzyObjectFactory;
import com.tvd12.ezyfox.pattern.EzyObjectPool;

public class MyTestObjectPool extends EzyObjectPool<MyTestObject> {

    protected MyTestObjectPool(Builder builder) {
        super(builder);
    }

    public MyTestObject borrowOne() {
        return borrowObject();
    }

    public void returnOne(MyTestObject object) {
        returnObject(object);
    }

    @Override
    public List<MyTestObject> getCanBeStaleObjects() {
        return super.getCanBeStaleObjects();
    }

    @Override
    public List<MyTestObject> getRemainObjects() {
        return super.getRemainObjects();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EzyObjectPool.Builder<MyTestObject, Builder> {

        @Override
        public MyTestObjectPool build() {
            return new MyTestObjectPool(this);
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
