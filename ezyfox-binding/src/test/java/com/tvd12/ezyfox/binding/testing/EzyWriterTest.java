package com.tvd12.ezyfox.binding.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.binding.annotation.EzyTemplateImpl;
import com.tvd12.ezyfox.entity.EzyObject;

public class EzyWriterTest {

    @Test
    public void test() {
        assert new ClassAWriter().getObjectType() == ClassA.class;
        assert new ClassATemplate().getObjectType() == ClassA.class;
    }

    public static class ClassA {
    }

    public static class ClassAWriter implements EzyWriter<ClassA, EzyObject> {

        @Override
        public EzyObject write(EzyMarshaller marshaller, ClassA object) {
            return null;
        }
    }

    @EzyTemplateImpl
    public static class ClassATemplate implements EzyWriter<ClassA, EzyObject> {


        @Override
        public EzyObject write(EzyMarshaller marshaller, ClassA object) {
            return null;
        }

    }
}