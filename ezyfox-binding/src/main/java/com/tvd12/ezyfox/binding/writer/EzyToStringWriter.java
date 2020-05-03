package com.tvd12.ezyfox.binding.writer;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyWriter;

public final class EzyToStringWriter implements EzyWriter<Object, Object> {

	private static final EzyToStringWriter INSTANCE = new EzyToStringWriter();
	
	private EzyToStringWriter() {
	}
	
	public static EzyToStringWriter getInstance() {
		return INSTANCE;
	}

	@Override
	public Object write(EzyMarshaller marshaller, Object object) {
		return object.toString();
	}
	
	
	
}
