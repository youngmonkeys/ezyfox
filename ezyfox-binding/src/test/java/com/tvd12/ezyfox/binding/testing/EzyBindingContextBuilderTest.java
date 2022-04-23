package com.tvd12.ezyfox.binding.testing;

import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.binding.testing.template.HelloWriter;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.reflect.FieldUtil;
import org.testng.annotations.Test;

import java.util.Map;

public class EzyBindingContextBuilderTest {

    @Test
    @SuppressWarnings("rawtypes")
    public void test() {
        // given
        EzyBindingContext bindingContext = EzyBindingContext.builder()
            .addTemplate(HelloWriter.class, new HelloWriter())
            .build();

        // when
        Map<Class, EzyWriter> writersByObjectType = FieldUtil.getFieldValue(
            bindingContext,
            "writersByObjectType"
        );

        // then
        Asserts.assertTrue(writersByObjectType.containsKey(HelloWriter.class));
    }
}
