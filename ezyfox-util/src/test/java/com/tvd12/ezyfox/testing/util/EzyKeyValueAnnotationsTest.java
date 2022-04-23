package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.annotation.EzyKeyValue;
import com.tvd12.ezyfox.util.EzyKeyValueAnnotations;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.util.Map;

public class EzyKeyValueAnnotationsTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyKeyValueAnnotations.class;
    }

    @Test
    public void test() {
        Map<String, String> map = EzyKeyValueAnnotations
            .getProperties(ClassE.class.getAnnotation(EzyAutoImpl.class)
                .properties());
        assert map.get("foo").equals("bar");
    }

    @EzyAutoImpl(properties = {
        @EzyKeyValue(key = "foo", value = "bar")
    })
    public interface ClassE {

    }
}
