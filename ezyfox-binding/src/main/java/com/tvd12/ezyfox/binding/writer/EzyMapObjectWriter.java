package com.tvd12.ezyfox.binding.writer;

import java.util.Map;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.util.EzyEntityBuilders;

@SuppressWarnings("rawtypes")
public final class EzyMapObjectWriter
        extends EzyEntityBuilders
        implements EzyWriter<Map, EzyObject> {

    private static final EzyMapObjectWriter INSTANCE = new EzyMapObjectWriter();

    private EzyMapObjectWriter() {}

    public static EzyMapObjectWriter getInstance() {
        return INSTANCE;
    }

    @Override
    public EzyObject write(EzyMarshaller marshaller, Map map) {
        EzyObjectBuilder builder = newObjectBuilder();
        for(Object key : map.keySet()) {
            Object tkey = marshaller.marshal(key);
            Object tvalue = marshaller.marshal(map.get(key));
            builder.append(tkey, tvalue);
        }
        return builder.build();
    }

    @Override
    public Class<?> getObjectType() { return null; }

}
