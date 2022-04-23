package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.binding.impl.EzyObjectWriterBuilder;
import com.tvd12.ezyfox.binding.impl.EzySimpleMarshaller;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;
import lombok.Getter;
import org.testng.annotations.Test;

public class EzySimpleMarshallerTest extends BaseTest {

    @Test
    public void test1() {
        EzySimpleMarshaller marshaller = new EzySimpleMarshaller();
        marshaller.addWriter(ClassA.class, new EzyObjectWriterBuilder(new EzyClass(ClassA.class)).build());
        ClassA[] arrayInput = new ClassA[]{new ClassA(), new ClassA(), new ClassA()};
        EzyArray arrayOutput = marshaller.marshal(arrayInput);
        assert arrayOutput.size() == 3;
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test2() {
        EzySimpleMarshaller marshaller = new EzySimpleMarshaller();
        marshaller.marshal(new Object());
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test3() {
        EzySimpleMarshaller marshaller = new EzySimpleMarshaller();
        marshaller.marshal(ExWriter.class, new Object());
    }

    @Test
    public void marshallerBooleanTest() {
        // given
        EzyBindingContext bindingContext = EzyBindingContext.builder()
            .addObjectBindingClass(ClassB.class)
            .build();

        EzyMarshaller sut = bindingContext.newMarshaller();

        ClassB classB = new ClassB();

        // when
        EzyObject actual = sut.marshal(classB);

        // then
        EzyObject expectation = EzyEntityFactory.newObjectBuilder()
            .append("correct", false)
            .build();
        Asserts.assertEquals(actual, expectation);
    }

    public static class ClassA {}

    @Getter
    @SuppressWarnings("unsed")
    public static class ClassB {
        private boolean correct;
    }

    @SuppressWarnings("rawtypes")
    public static class ExWriter implements EzyWriter {

        @Override
        public Object write(EzyMarshaller marshaller, Object object) {
            return null;
        }

    }
}
