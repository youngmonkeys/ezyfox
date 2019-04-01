package com.tvd12.ezyfox.util;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;

public class EzyObjectToMap {

	private final EzyArrayToList arrayToList;
	private static final EzyObjectToMap INSTANCE = new EzyObjectToMap();
	
	private EzyObjectToMap() {
		this.arrayToList = EzyArrayToList.getInstance();
	}
	
	public static EzyObjectToMap getInstance() {
		return INSTANCE;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map toMap(EzyObject object) {
		Map answer = new HashMap<>();
		for(Object key : object.keySet()) {
			Object value = object.get(key);
			Object skey = key;
			if(key instanceof EzyArray)
				skey = arrayToList.toList((EzyArray)key);
			else if(key instanceof EzyObject)
				skey = toMap((EzyObject) key);
			Object svalue = value;
			if(value instanceof EzyArray)
				svalue = arrayToList.toList((EzyArray) value);
			if(value instanceof EzyObject)
				svalue = toMap((EzyObject) value);
			answer.put(skey, svalue);
		}
		return answer;
	}
	
}
