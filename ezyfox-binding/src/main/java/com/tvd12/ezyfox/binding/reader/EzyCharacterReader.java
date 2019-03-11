package com.tvd12.ezyfox.binding.reader;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;

public final class EzyCharacterReader implements EzyReader<Object, Character> {

	private static final EzyCharacterReader INSTANCE = new EzyCharacterReader();
	
	private EzyCharacterReader() {
	}
	
	public static EzyCharacterReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	public Character read(EzyUnmarshaller unmarshaller, Object value) {
		if(value instanceof Character)
			return (Character)value;
		if(value instanceof Number)
			return (char)((Number)value).byteValue();
		String str = value.toString();
		if(str.isEmpty())
			return 0;
		return str.charAt(0);
	}
	
}
