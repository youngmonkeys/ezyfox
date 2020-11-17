package com.tvd12.ezyfox.example.reflection;

import java.util.List;

import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.ezyfox.reflect.EzyMethod;

public class EzyReflectionExample {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Object string = EzyClasses.newInstance("java.lang.String");
		System.out.println(string);
		
		EzyClass stringClass = new EzyClass(String.class);
		List<EzyMethod> stringMethods = stringClass.getDeclaredMethods();
	}
	
}
