package com.tvd12.ezyfox.jackson;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.tvd12.ezyfox.entity.EzyRoArray;

public class JacksonArraySerializer extends StdSerializer<EzyRoArray> {
	private static final long serialVersionUID = 47227884568344818L;
	
	public JacksonArraySerializer() {
		super(EzyRoArray.class);
	}

	@Override
	public void serialize(EzyRoArray array, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeStartArray();
		for(int i = 0 ; i < array.size() ; ++i) {
			Object item = array.get(i);
			gen.writeObject(item);
		}
		gen.writeEndArray();
	}
	
	

}
