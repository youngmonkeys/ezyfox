package com.tvd12.ezyfox.binding.testing.unwrapper;

import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.EzyUnwrapper;
import com.tvd12.ezyfox.entity.EzyArray;

@SuppressWarnings("rawtypes")
public class ClassAUnwrapper implements EzyUnwrapper {

    @Override
    public void unwrap(EzyUnmarshaller arg0, Object arg1, Object arg2) {
        EzyArray value = (EzyArray)arg1;
        ClassA object = (ClassA)arg2;
        object.setA(value.get(0));
    }
}
