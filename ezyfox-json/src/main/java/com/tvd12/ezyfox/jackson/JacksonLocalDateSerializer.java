package com.tvd12.ezyfox.jackson;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.tvd12.ezyfox.io.EzyDates;

public class JacksonLocalDateSerializer extends StdSerializer<LocalDate> {
	private static final long serialVersionUID = 47227884568344818L;
	
	public JacksonLocalDateSerializer() {
		super(LocalDate.class);
	}

	@Override
	public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeString(EzyDates.format(value, EzyDates.DATE_PATTERN));
	}
}
