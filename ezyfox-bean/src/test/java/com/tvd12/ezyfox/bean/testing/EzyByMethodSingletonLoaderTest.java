package com.tvd12.ezyfox.bean.testing;

import java.lang.reflect.Method;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.impl.EzyByMethodSingletonLoader;
import com.tvd12.ezyfox.io.EzyMaps;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.test.base.BaseTest;

public class EzyByMethodSingletonLoaderTest extends BaseTest {

	public A newA() {
		return new A();
	}
	
	public B newB() {
		return new B();
	}
	
	@Test
	public void test() throws Exception {
		EzyBeanContext context = EzyBeanContext.builder()
				.build();
		EzyMethod methodA = new EzyMethod(getClass().getDeclaredMethod("newA"));
		EzyMethod methodB = new EzyMethod(getClass().getDeclaredMethod("newB"));
		EzyByMethodSingletonLoader loader = new EzyByMethodSingletonLoader(
				"a",
				methodA, this, EzyMaps.newHashMap(B.class, methodB));
		
		Method getConstructorParameterTypes = EzyByMethodSingletonLoader.class
				.getDeclaredMethod("getConstructorParameterTypes", Class.class);
		getConstructorParameterTypes.setAccessible(true);
		getConstructorParameterTypes.invoke(loader, B.class);
		getConstructorParameterTypes.invoke(loader, Object.class);
		
		loader.load(context);
	}
	
	public static class A {
		@EzyAutoBind
		public B b;
	}
	
	public static class B {
		
	}
	
}
