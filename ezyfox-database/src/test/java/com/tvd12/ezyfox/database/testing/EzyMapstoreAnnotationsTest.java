package com.tvd12.ezyfox.database.testing;

import com.tvd12.ezyfox.database.testing.mapstore.PersonMapstore;
import com.tvd12.ezyfox.database.testing.mapstore.UserMapstore;
import com.tvd12.ezyfox.database.util.EzyMapstoreAnnotations;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyMapstoreAnnotationsTest extends BaseTest {

    @Test
    public void test() {
        assert EzyMapstoreAnnotations.getMapName(new PersonMapstore()).equals("personMapstore");
        assert EzyMapstoreAnnotations.getMapName(new UserMapstore()).equals("user");
    }

    @Override
    public Class<?> getTestClass() {
        return EzyMapstoreAnnotations.class;
    }
}
