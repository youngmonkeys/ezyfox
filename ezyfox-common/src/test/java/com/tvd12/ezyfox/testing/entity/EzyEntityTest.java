package com.tvd12.ezyfox.testing.entity;

import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.test.base.BaseTest;

public class EzyEntityTest extends BaseTest {

    protected EzyArrayBuilder newArrayBuilder() {
        return EzyEntityFactory.create(EzyArrayBuilder.class);
    }

    protected EzyObjectBuilder newObjectBuilder() {
        return EzyEntityFactory.create(EzyObjectBuilder.class);
    }

}
