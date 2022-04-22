package com.tvd12.ezyfox.binding.testing.unwrapper;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.impl.EzyObjectUnwrapperBuilder;
import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.test.base.BaseTest;

public class EzyUnwrapperTest extends BaseTest {

    @Test
    public void test() {
        EzyBindingContext context = EzyBindingContext.builder()
                .scan("com.tvd12.ezyfox.binding.testing.unwrapper")
                .build();
        EzyObjectUnwrapperBuilder.setDebug(true);
        EzyArray array = EzyEntityFactory.create(EzyArrayBuilder.class)
                .append("hehe")
                .build();
        EzyUnmarshaller unmarshaller = context.newUnmarshaller();
        ClassA classA = new ClassA();
        unmarshaller.unwrap(array, classA);
        System.out.println(classA);
    }

}
