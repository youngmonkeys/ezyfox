package com.tvd12.ezyfox.jackson;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JacksonInstantSerializer extends StdSerializer<Instant> {
    private static final long serialVersionUID = 47227884568344818L;

    public JacksonInstantSerializer() {
        super(Instant.class);
    }

    @Override
    public void serialize(Instant value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(value.toEpochMilli());
    }


}
