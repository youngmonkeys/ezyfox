package com.tvd12.ezyfox.example.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.ezyfox.reflect.EzyGenerics;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.reflect.EzyMethods;

public class EzyReflectionExample {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Object string = EzyClasses.newInstance("java.lang.String");
		System.out.println(string);
		
		EzyClass stringClass = new EzyClass(String.class);
		List<EzyMethod> stringMethods = stringClass.getDeclaredMethods();
		
		Method getStringsMethod = EzyMethods.getMethod(EzyReflectionExample.class, "getStrings");
		Type genericReturnType = getStringsMethod.getGenericReturnType();
		Class<?> genericClass = EzyGenerics.getOneGenericClassArgument(genericReturnType);
		System.out.println("genericClass: " + genericClass);
	}
	
	public List<String> getStrings() {
		return Collections.emptyList();
	}
	
}
