package com.tvd12.ezyfox.bean.testing;

import java.util.HashMap;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.impl.EzyByFieldSingletonLoader;
import com.tvd12.ezyfox.reflect.EzyField;
import com.tvd12.test.base.BaseTest;

public class EzyByFieldSingletonLoaderTest extends BaseTest {

	public A a;
	
	@Test
	public void test() throws Exception {
		ExEzyByFieldSingletonLoader loader = new ExEzyByFieldSingletonLoader(
				new EzyField(getClass().getDeclaredField("a")), this);
		loader.getConstructorArgumentNames();
		loader.getConstructorParameterTypes(getClass());
	}
	
	public static class ExEzyByFieldSingletonLoader extends EzyByFieldSingletonLoader {
		public ExEzyByFieldSingletonLoader(EzyField field, Object configurator) {
			super("a", field, configurator, new HashMap<>());
		}

		@Override
		public String[] getConstructorArgumentNames() {
			return super.getConstructorArgumentNames();
		}
		
		@SuppressWarnings("rawtypes")
		@Override
		public Class[] getConstructorParameterTypes(Class clazz) {
			return super.getConstructorParameterTypes(clazz);
		}
	}
	
	public static class A {
		public String value = "v";
	}
	
}
