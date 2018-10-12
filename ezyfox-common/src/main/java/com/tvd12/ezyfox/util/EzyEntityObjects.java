package com.tvd12.ezyfox.util;

import java.util.Map;

import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

@SuppressWarnings("rawtypes")
public final class EzyEntityObjects {

	private EzyEntityObjects() {
	}
	
	public static EzyObject newObject(Map map) {
		EzyObject obj = EzyEntityFactory.newObject();
		obj.putAll(map);
		return obj;
	}
	
}
