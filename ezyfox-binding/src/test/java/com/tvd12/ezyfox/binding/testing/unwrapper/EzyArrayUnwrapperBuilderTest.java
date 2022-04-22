package com.tvd12.ezyfox.binding.testing.unwrapper;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.EzyUnwrapper;
import com.tvd12.ezyfox.binding.impl.EzyArrayUnwrapperBuilder;
import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.test.base.BaseTest;

public class EzyArrayUnwrapperBuilderTest extends BaseTest {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void test() {
        EzyBindingContext context = EzyBindingContext.builder()
                .build();
        EzyArrayUnwrapperBuilder.setDebug(true);
        EzyArrayUnwrapperBuilder builder =
                new EzyArrayUnwrapperBuilder(new EzyClass(ClassA.class));
        EzyArray array = EzyEntityFactory.create(EzyArrayBuilder.class)
                .append("hehe")
                .build();
        EzyUnmarshaller unmarshaller = context.newUnmarshaller();
        EzyUnwrapper unwrapper = builder.build();
        ClassA classA = new ClassA();
        unwrapper.unwrap(unmarshaller, array, classA);
        System.out.println(classA);
    }
}