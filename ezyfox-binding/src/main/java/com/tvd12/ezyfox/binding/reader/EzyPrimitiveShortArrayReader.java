package com.tvd12.ezyfox.binding.reader;

public class EzyPrimitiveShortArrayReader extends EzyPrimitiveArrayReader {

	private static final EzyPrimitiveShortArrayReader INSTANCE = new EzyPrimitiveShortArrayReader();
	
	private EzyPrimitiveShortArrayReader() {
	}
	
	public static EzyPrimitiveShortArrayReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	protected Class<?> getOutType() {
		return short.class;
	}

	@Override
	protected Object newArray(int length) {
		return new short[length];
	}
	
	@Override
	protected void setValue(Object array, int index, Object value) {
		((short[])array)[index] = ((Number)value).shortValue();
	}

}
