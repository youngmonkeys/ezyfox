package com.tvd12.ezyfox.binding.reader;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;

public final class EzyLongReader implements EzyReader<Number, Long> {

	private static final EzyLongReader INSTANCE = new EzyLongReader();
	
	private EzyLongReader() {
	}
	
	public static EzyLongReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	public Long read(EzyUnmarshaller unmarshaller, Number value) {
		return value.longValue();
	}
	
}
