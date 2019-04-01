package com.tvd12.ezyfox.util;

import java.util.ArrayList;
import java.util.List;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;

public final class EzyArrayToList {
	
	private final EzyObjectToMap objectToMap;
	private static final EzyArrayToList INSTANCE = new EzyArrayToList();
	
	private EzyArrayToList() {
		this.objectToMap = EzyObjectToMap.getInstance();
	}
	
	public static EzyArrayToList getInstance() {
		return INSTANCE;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List toList(EzyArray array) {
		List list = new ArrayList<>();
		for(int i = 0 ; i < array.size() ; i++) {
			Object item = list.get(i);
			Object sitem = item;
			if(item instanceof EzyObject)
				sitem = objectToMap.toMap((EzyObject) item);
			else if(item instanceof EzyArray)
				sitem = toList((EzyArray) item);
			list.add(sitem);
		}
		return list;
	}
	
}
