package com.tvd12.ezyfox.tool;

import java.lang.reflect.Constructor;

public class EzyReflectTool {

	public static Constructor<?> getConstructor(Class<?> clazz) {
		try {
			return clazz.getConstructors()[0];
		}
		catch (Exception e) {
			throw new IllegalArgumentException("has no contructor of: " + clazz, e);
		}
	}
	
}
