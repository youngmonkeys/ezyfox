package com.tvd12.ezyfox.binding.reader;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;

public class EzyDefaultReader implements EzyReader<Object, Object> {

	private static final EzyDefaultReader INSTANCE = new EzyDefaultReader();
	
	private EzyDefaultReader() {
	}
	
	public static EzyDefaultReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	public Object read(EzyUnmarshaller unmarshaller, Object value) {
		return value;
	}
	
}
