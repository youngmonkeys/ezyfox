package com.tvd12.ezyfox.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.tvd12.ezyfox.io.EzyDates;

import java.io.IOException;
import java.util.Date;

public class JacksonDateSerializer extends StdSerializer<Date> {
    private static final long serialVersionUID = 47227884568344818L;

    public JacksonDateSerializer() {
        super(Date.class);
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(EzyDates.format(value));
    }
}
