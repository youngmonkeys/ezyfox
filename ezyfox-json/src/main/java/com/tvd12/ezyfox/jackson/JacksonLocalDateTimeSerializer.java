package com.tvd12.ezyfox.jackson;

import java.io.IOException;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.tvd12.ezyfox.io.EzyDates;

public class JacksonLocalDateTimeSerializer extends StdSerializer<LocalDateTime> {
    private static final long serialVersionUID = 47227884568344818L;

    public JacksonLocalDateTimeSerializer() {
        super(LocalDateTime.class);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(EzyDates.format(value));
    }


}