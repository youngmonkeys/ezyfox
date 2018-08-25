package com.tvd12.ezyfox.testing.reflect;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.List;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.reflect.EzyMethods;
import com.tvd12.test.base.BaseTest;

public class EzyMethodsTest extends BaseTest {

	@Override
	public Class<?> getTestClass() {
		return EzyMethods.class;
	}
	
	@Test
	public void test() {
		assertNotNull(EzyMethods.getPublicMethod(ClassB.class, "setValue", String.class));
		assertEquals(EzyMethods.getAnnotatedMethods(ClassB.class, ExampleAnnotation.class).size(), 1);
	}
	
	@Test
	public void test2() {
		List<Method> methods = EzyMethods.getPublicMethods(ClassC.class);
		System.out.println("public methods: " + methods);
		assertEquals(methods.size(), 5);
	}
	
	public static class ClassC extends ClassB {
		
		public static void c1() {
		}
		
		public static final void c2() {
		}
		
		public final void c3() {
		}
	}
	
	public static class ClassA {
		
		public void setValue(String value) {
		}
		
	}
	
	public static class ClassB extends ClassA {
		
		@ExampleAnnotation
		public void setName(String name) {
			
		}
	}
	
}
