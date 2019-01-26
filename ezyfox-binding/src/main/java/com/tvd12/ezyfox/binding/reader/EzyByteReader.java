package com.tvd12.ezyfox.binding.reader;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;

public class EzyByteReader implements EzyReader<Number, Byte> {

	private static final EzyByteReader INSTANCE = new EzyByteReader();
	
	private EzyByteReader() {
	}
	
	public static EzyByteReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	public Byte read(EzyUnmarshaller unmarshaller, Number value) {
		return value.byteValue();
	}
	
}
