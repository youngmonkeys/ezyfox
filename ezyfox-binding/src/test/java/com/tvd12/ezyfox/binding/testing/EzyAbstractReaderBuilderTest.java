package com.tvd12.ezyfox.binding.testing;

import java.lang.reflect.Parameter;
import java.util.List;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.asm.EzyInstruction;
import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.impl.EzyAbstractReaderBuilder;
import com.tvd12.ezyfox.binding.impl.EzyElementsFetcher;
import com.tvd12.ezyfox.binding.impl.EzyObjectElementsFetcher;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.reflect.EzySetterMethod;
import com.tvd12.test.base.BaseTest;

public class EzyAbstractReaderBuilderTest extends BaseTest {

    @Test(expectedExceptions = {IllegalStateException.class})
    public void test1() {
        new Builder1(new EzyClass(getClass())).build();
    }

    @Test
    public void appendConstructorParamValueTest() {
        Builder1 builder1 = new Builder1(new EzyClass(ClassA.class));
        builder1.appendConstructorParamValue(null, null, 1, null, null);
    }

    public static class Builder1 extends EzyAbstractReaderBuilder {
        public Builder1(EzyClass clazz) {
            super(clazz);
        }

        @Override
        protected int getAccessType(EzyClass clazz) {
            return EzyAccessType.ALL;
        }

        @Override
        public void appendConstructorParamValue(
                EzyInstruction instruction,
                Parameter parameter,
                int parameterIndex,
                EzyField field,
                String key) {
            super.appendConstructorParamValue(instruction, parameter, parameterIndex, field, key);
        }

        @Override
        protected EzyElementsFetcher newElementsFetcher() {
            return new EzyObjectElementsFetcher() {

                @Override
                protected List<? extends EzyMethod> getMethodList(EzyClass clazz) {
                    return clazz.getSetterMethods();
                }

                @Override
                protected List<? extends EzyMethod> getDeclaredMethods(EzyClass clazz) {
                    return clazz.getDeclaredSetterMethods();
                }

                @Override
                protected boolean isValidAnnotatedMethod(EzyMethod method) {
                    return method.getParameterCount() == 1;
                }

                @Override
                protected EzyMethod newByFieldMethod(EzyMethod method) {
                    return new EzySetterMethod(method);
                }
            };
        }

        @SuppressWarnings("rawtypes")
        @Override
        protected EzyReader make() throws Exception {
            throw new Exception();
        }

        @Override
        protected String getImplClassName() {
            return "";
        }

        @Override
        protected String makeImplMethodContent(EzyMethod readMethod) {
            return "";
        }

        @Override
        protected boolean isDebug() {
            return false;
        }

    }

    public static class ClassA {}
}
