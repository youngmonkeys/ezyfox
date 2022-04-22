package com.tvd12.ezyfox.binding.testing;

import java.util.Collection;
import java.util.Map;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.binding.annotation.EzyValue;
import com.tvd12.ezyfox.binding.impl.EzyObjectReaderBuilder;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.util.EzyEntityObjects;
import com.tvd12.test.base.BaseTest;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class EzyObjectReaderBuilderTest extends BaseTest {

    @Test
    public void test() {
        EzyObjectReaderBuilder.setDebug(true);
        EzyObjectReaderBuilder builder = new EzyObjectReaderBuilder(new EzyClass(ClassA.class));
        builder.build();
    }
    
    @Test
    public void testConstructorCase() {
        EzyObjectReaderBuilder.setDebug(true);
        EzyObjectReaderBuilder builder = new EzyObjectReaderBuilder(new EzyClass(ClassB.class));
        builder.build();
    }
    
    @Test
    public void testConstructorNoFieldsCase() {
        EzyObjectReaderBuilder.setDebug(true);
        EzyObjectReaderBuilder builder = new EzyObjectReaderBuilder(new EzyClass(ClassC.class));
        builder.build();
    }
    
    @Test
    public void readToFinalFieldCase() {
        // given
        EzyObjectReaderBuilder.setDebug(true);
        EzyBindingContext bindingContext = EzyBindingContext.builder()
                .addClass(ClassD.class)
                .build();

        // when
        EzyUnmarshaller unmarshaller = bindingContext.newUnmarshaller();
        EzyObject value = EzyEntityObjects.newObject("name", "Foo");
        ClassD classD = unmarshaller.unmarshal(value, ClassD.class);
        
        // then
        assert classD.name.equals("name");
        
    }

    @SuppressWarnings("rawtypes")
    public static class ClassA {
        public Map map;
        public void setCollection(Collection collection) {
        }
    }
    
    @Getter
    @AllArgsConstructor
    public static class ClassB {
        private String name;
        private String value;
    }
    
    @Getter
    public static class ClassC {
        public ClassC(
                boolean booleanValue,
                byte byteValue,
                char charValue,
                double doubleValue,
                float floatValue,
                int intValue,
                long longValue,
                short shortValue,
                String stringValue) {}
    }
    
    @Getter
    @EzyObjectBinding
    public static class ClassD {
        @EzyValue("name")
        public final String name = "name"; 
    }
    }