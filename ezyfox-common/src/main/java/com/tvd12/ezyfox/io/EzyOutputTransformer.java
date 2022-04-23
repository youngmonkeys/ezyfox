package com.tvd12.ezyfox.io;

import java.io.Serializable;

public interface EzyOutputTransformer extends Serializable {

    @SuppressWarnings("rawtypes")
    Object transform(Object value, Class type);
}
