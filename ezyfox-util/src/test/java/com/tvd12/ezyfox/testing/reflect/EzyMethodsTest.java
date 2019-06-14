package com.tvd12.ezyfox.testing.reflect;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyMethod;
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
	
	@Test
	public void test3() {
		EzyClass clazz = new EzyClass(World.class);
		Collection<EzyMethod> methods = clazz.getMethods();
		System.out.println("test3: " + methods);
	}
	
	@Test
	public void test4() {
		List<Method> methods = EzyMethods.getMethods(ClassA2.class);
		assert EzyMethods.filterOverriddenMethods(methods).size() >= 2;
		EzyClass clazz = new EzyClass(ClassA2.class);
		List<EzyMethod> ms = clazz.getMethods();
		assert EzyMethods.filterOverriddenMethods(ms).size() >= 2;
	}
	
	@Test
	public void test5() throws Exception {
		Method m1 = ClassA1.class.getDeclaredMethod("setA");
		Method m2 = ClassA2.class.getDeclaredMethod("setA");
		assert !EzyMethods.isOverriddenMethod(m1, m1);
		assert EzyMethods.isOverriddenMethod(m1, m2);
		assert EzyMethods.isOverriddenMethod(m2, m1);
		Method m3 = ClassA1.class.getDeclaredMethod("setA1");
		assert !EzyMethods.isOverriddenMethod(m1, m3);
		assert !EzyMethods.isOverriddenMethod(m3, m1);
		assert !EzyMethods.isOverriddenMethod(m2, m3);
		assert !EzyMethods.isOverriddenMethod(m3, m2);
		Method m4 = ClassA2.class.getDeclaredMethod("setA1", String.class);
		assert !EzyMethods.isOverriddenMethod(m3, m4);
		assert !EzyMethods.isOverriddenMethod(m4, m3);
		Method m5 = ClassA3.class.getDeclaredMethod("setA1", String.class);
		assert !EzyMethods.isOverriddenMethod(m4, m5);
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void test6() throws Exception {
		EzyMethods.isOverriddenMethod((Method)null, null);
	}
	
	public static class ClassA3 {
		public void setA1(String str) {
		}
	}
	
	public static class ClassA2 extends ClassA1 {

		public void setB() {
		}
		
		@Override
		public void setA() {
			super.setA();
		}
		
		public void setA1(String str) {
		}
		
	}
	
	public static class ClassA1 {
		
		public void setA() {
		}
		
		public void setA1() {
		}
		
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
	
	public static interface Hello {
		
		String getMessageType();
		
	}
	
	public static class World implements Hello {

		@Override
		public String getMessageType() {
			return "hello world";
		}
		
	}
	
}
