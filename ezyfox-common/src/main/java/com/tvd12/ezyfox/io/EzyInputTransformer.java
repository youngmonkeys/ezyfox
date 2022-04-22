package com.tvd12.ezyfox.io;

import java.io.Serializable;

public interface EzyInputTransformer extends Serializable {

    Object transform(Object value);
}