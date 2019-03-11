package com.tvd12.ezyfox.binding.reader;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;

public final class EzyFloatReader implements EzyReader<Number, Float> {

	private static final EzyFloatReader INSTANCE = new EzyFloatReader();
	
	private EzyFloatReader() {
	}
	
	public static EzyFloatReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	public Float read(EzyUnmarshaller unmarshaller, Number value) {
		return value.floatValue();
	}
	
}
