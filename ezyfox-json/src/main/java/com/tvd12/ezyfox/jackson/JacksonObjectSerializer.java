package com.tvd12.ezyfox.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.tvd12.ezyfox.entity.EzyRoObject;

public class JacksonObjectSerializer extends StdSerializer<EzyRoObject> {
    private static final long serialVersionUID = 1033303749441882688L;

    public JacksonObjectSerializer() {
        super(EzyRoObject.class);
    }

    @Override
    public void serialize(EzyRoObject object, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        for(Object key : object.keySet()) {
            Object value = object.get(key);
            gen.writeObjectField(key.toString(), value);
        }
        gen.writeEndObject();
    }
}
