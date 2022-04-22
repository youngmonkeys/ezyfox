package com.tvd12.ezyfox.binding;

public interface EzyUnwrapper<I,O> {

    void unwrap(EzyUnmarshaller unmarshaller, I input, O output);

}
