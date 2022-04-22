package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

public class EzyEntityBuilders extends EzyLoggable {

    protected EzyArray newArray() {
        return EzyEntityFactory.newArray();
    }

    protected EzyObject newObject() {
        return EzyEntityFactory.newObject();
    }

    protected EzyArrayBuilder newArrayBuilder() {
        return EzyEntityFactory.newArrayBuilder();
    }

    protected EzyObjectBuilder newObjectBuilder() {
        return EzyEntityFactory.newObjectBuilder();
    }
}