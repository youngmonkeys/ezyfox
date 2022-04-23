package com.tvd12.ezyfox.binding.testing.scan2;

import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.annotation.EzyReaderImpl;
import com.tvd12.ezyfox.binding.impl.EzyAbstractReader;
import com.tvd12.ezyfox.entity.EzyObject;

@EzyReaderImpl
public class Scan2ObjectReader extends EzyAbstractReader<EzyObject, Scan2Object> {

    @Override
    public Scan2Object read(EzyUnmarshaller unmarshaller, EzyObject value) {
        return new Scan2Object();
    }
}
