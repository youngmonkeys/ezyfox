package com.tvd12.ezyfox.testing.factory;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzySimpleEntityCreator;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzySimpleEntityCreatorTest extends BaseTest {

    @Test
    public void test() {
        EzySimpleEntityCreator instance = EzySimpleEntityCreator.getInstance();
        instance.newArray();
        instance.newObject();
        instance.create(EzyObject.class);
        instance.create(EzyArray.class);
    }
}
