package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.binding.impl.EzyAbstractWriterBuilder;
import com.tvd12.ezyfox.binding.impl.EzyElementsFetcher;
import com.tvd12.ezyfox.binding.impl.EzyObjectElementsFetcher;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyGetterMethod;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.util.List;

public class EzyAbstractWriterBuilderTest extends BaseTest {

    @Test(expectedExceptions = {IllegalStateException.class})
    public void test() {
        new Builder1(new EzyClass(getClass())).build();
    }

    public static class Builder1 extends EzyAbstractWriterBuilder {

        public Builder1(EzyClass clazz) {
            super(clazz);
        }

        @Override
        protected Class<?> getOutType() {
            return null;
        }

        @Override
        protected int getAccessType(EzyClass clazz) {
            return EzyAccessType.ALL;
        }

        @Override
        protected EzyElementsFetcher newElementsFetcher() {
            return new EzyObjectElementsFetcher() {

                @Override
                protected List<? extends EzyMethod> getMethodList(EzyClass clazz) {
                    return clazz.getGetterMethods();
                }

                @Override
                protected List<? extends EzyMethod> getDeclaredMethods(EzyClass clazz) {
                    return clazz.getDeclaredGetterMethods();
                }

                @Override
                protected boolean isValidAnnotatedMethod(EzyMethod method) {
                    return method.getReturnType() != void.class;
                }

                @Override
                protected EzyMethod newByFieldMethod(EzyMethod method) {
                    return new EzyGetterMethod(method);
                }

            };
        }

        @SuppressWarnings("rawtypes")
        @Override
        protected EzyWriter make() throws Exception {
            throw new Exception();
        }

        @Override
        protected String getImplClassName() {
            return "";
        }

        @Override
        protected String makeImplMethodContent(EzyMethod writeMethod) {
            return "";
        }

        @Override
        protected boolean isDebug() {
            return false;
        }

    }
}
