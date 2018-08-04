package com.tvd12.ezyfox.reflect;

import java.util.Set;

import com.tvd12.ezyfox.reflect.EzyClasses;

public final class EzyInterfaces {

	private EzyInterfaces() {
	}
	
	@SuppressWarnings("rawtypes")
	public static Class getInterface(Class clazz, Class interfaceClass) {
		Set<Class> interfaces = EzyClasses.flatInterfaces(clazz);
		for(Class itf : interfaces) {
			if(itf.equals(interfaceClass))
				return itf;
		}
		return null;
			
	}
	
}
