package com.tvd12.ezyfox.binding.writer;

import java.util.Map;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.util.EzyEntityBuilders;

@SuppressWarnings("rawtypes")
public final class EzyMapArrayWriter
        extends EzyEntityBuilders
        implements EzyWriter<Map, EzyArray> {

    private static final EzyMapArrayWriter INSTANCE = new EzyMapArrayWriter();

    private EzyMapArrayWriter() {}

    public static EzyMapArrayWriter getInstance() {
        return INSTANCE;
    }

    @Override
    public EzyArray write(EzyMarshaller marshaller, Map map) {
        EzyArrayBuilder arrayBuilder = newArrayBuilder();
        for(Object key : map.keySet()) {
            EzyObjectBuilder objectBuilder = newObjectBuilder();
            Object tkey = marshaller.marshal(key);
            Object tvalue = marshaller.marshal(map.get(key));
            objectBuilder.append("k", tkey);
            objectBuilder.append("v", tvalue);
            arrayBuilder.append(objectBuilder);
        }
        return arrayBuilder.build();
    }

    @Override
    public Class<?> getObjectType() { return null; }

}
