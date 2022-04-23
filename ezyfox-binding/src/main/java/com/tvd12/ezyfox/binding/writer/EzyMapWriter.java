package com.tvd12.ezyfox.binding.writer;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

import java.util.Map;

@SuppressWarnings("rawtypes")
public final class EzyMapWriter implements EzyWriter<Map, EzyObject> {

    private static final EzyMapWriter INSTANCE = new EzyMapWriter();

    private EzyMapWriter() {}

    public static EzyMapWriter getInstance() {
        return INSTANCE;
    }

    @Override
    public EzyObject write(EzyMarshaller marshaller, Map map) {
        EzyObjectBuilder builder = EzyEntityFactory.newObjectBuilder();
        for (Object key : map.keySet()) {
            Object value = map.get(key);
            Object mkey = marshaller.marshal(key);
            Object mvalue = marshaller.marshal(value);
            builder.append(mkey, mvalue);
        }
        return builder.build();
    }
}
