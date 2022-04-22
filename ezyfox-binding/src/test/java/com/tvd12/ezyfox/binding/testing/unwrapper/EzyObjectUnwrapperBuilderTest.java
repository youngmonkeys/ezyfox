package com.tvd12.ezyfox.binding.testing.unwrapper;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.EzyUnwrapper;
import com.tvd12.ezyfox.binding.impl.EzyObjectUnwrapperBuilder;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.test.base.BaseTest;

public class EzyObjectUnwrapperBuilderTest extends BaseTest {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void test() {
        EzyBindingContext context = EzyBindingContext.builder()
                .build();
        EzyObjectUnwrapperBuilder.setDebug(true);
        EzyObjectUnwrapperBuilder builder =
                new EzyObjectUnwrapperBuilder(new EzyClass(ClassA.class));
        EzyObject object = EzyEntityFactory.create(EzyObjectBuilder.class)
                .append("a", "hi!")
                .build();
        EzyUnmarshaller unmarshaller = context.newUnmarshaller();
        EzyUnwrapper unwrapper = builder.build();
        ClassA classA = new ClassA();
        unwrapper.unwrap(unmarshaller, object, classA);
        System.out.println(classA);
    }

}
