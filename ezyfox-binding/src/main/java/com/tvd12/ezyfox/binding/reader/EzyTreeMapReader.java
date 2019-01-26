package com.tvd12.ezyfox.binding.reader;

import java.util.Map;
import java.util.TreeMap;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.entity.EzyObject;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class EzyTreeMapReader implements EzyReader<EzyObject, Map> {

	private static final EzyTreeMapReader INSTANCE = new EzyTreeMapReader();
	
	private EzyTreeMapReader() {
	}
	
	public static EzyTreeMapReader getInstance() {
		return INSTANCE;
	}
	
	@Override
	public Map read(EzyUnmarshaller unmarshaller, EzyObject object) {
		Map answer = new TreeMap<>();
		for(Object key : object.keySet())
			answer.put(key, object.get(key));
		return answer;
	}
	
}
