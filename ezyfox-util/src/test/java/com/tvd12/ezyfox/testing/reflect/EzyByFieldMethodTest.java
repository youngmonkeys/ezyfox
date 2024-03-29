package com.tvd12.ezyfox.testing.reflect;

import com.tvd12.ezyfox.reflect.EzyByFieldMethod;
import com.tvd12.test.assertion.Asserts;
import lombok.Getter;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class EzyByFieldMethodTest {

    @Test
    public void getFieldNameTest() throws Exception {
        // given
        Method method = InternalClass.class.getDeclaredMethod("getName");

        EzyByFieldMethod sut = new InternalFieldMethod(method);

        // when
        String fieldName = sut.getFieldName();

        // then
        Asserts.assertEquals(fieldName, "name");
    }

    private static class InternalFieldMethod extends EzyByFieldMethod {

        public InternalFieldMethod(Method method) {
            super(method);
        }

        @Override
        public Type getGenericType() {
            return null;
        }

        @SuppressWarnings("rawtypes")
        @Override
        public Class getType() {
            return null;
        }
    }

    @Getter
    @SuppressWarnings("unused")
    private static class InternalClass {
        private String name;
    }
}
