package com.tvd12.ezyfox.entity;

import com.tvd12.ezyfox.io.EzyInputTransformer;
import com.tvd12.ezyfox.io.EzyOutputTransformer;

public class EzyTransformable {

    protected final EzyInputTransformer inputTransformer;
    protected final EzyOutputTransformer outputTransformer;

    public EzyTransformable(
        EzyInputTransformer inputTransformer,
        EzyOutputTransformer outputTransformer) {
        this.inputTransformer = inputTransformer;
        this.outputTransformer = outputTransformer;
    }
}
