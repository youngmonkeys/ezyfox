package com.tvd12.ezyfox.binding.reader;

import java.math.BigDecimal;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;

public final class EzyBigDecimalReader implements EzyReader<Object, BigDecimal> {

	private static final EzyBigDecimalReader INSTANCE = new EzyBigDecimalReader();
	
	private EzyBigDecimalReader() {
	}
	
	public static EzyBigDecimalReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	public BigDecimal read(EzyUnmarshaller unmarshaller, Object value) {
		if(value instanceof BigDecimal)
			return (BigDecimal)value;
		if(value instanceof Double)
			return new BigDecimal((Double)value);
		if(value instanceof Float)
			return new BigDecimal((Float)value);
		if(value instanceof Number)
			return new BigDecimal(((Number)value).longValue());
		if(value instanceof String)
			return new BigDecimal((String)value);
		throw new IllegalArgumentException("can't convert: " + value + " to BigDecimal");
	}
	
}
