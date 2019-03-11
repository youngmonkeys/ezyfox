package com.tvd12.ezyfox.binding.reader;

import java.util.HashSet;
import java.util.Set;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.entity.EzyArray;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class EzySetReader implements EzyReader<EzyArray, Set> {

	private static final EzySetReader INSTANCE = new EzySetReader();
	
	private EzySetReader() {
	}
	
	public static EzySetReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	public Set read(EzyUnmarshaller unmarshaller, EzyArray value) {
		Set answer = new HashSet<>();
		for(int i = 0 ; i < value.size() ; i++)
			answer.add(value.get(i));
		return answer;
	}
	
}
