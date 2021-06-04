package com.tvd12.ezyfox.testing.reflect;

import java.lang.reflect.Constructor;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;

public class EzyClassesTest extends BaseTest {

	@Test
	public void test() {
		EzyClasses.newInstance(String.class.getName());
		EzyClasses.newInstance(String.class.getName(), getClass().getClassLoader());
	}
	
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void test1() {
		EzyClasses.newInstance("aaa");
		EzyClasses.newInstance("aaa", getClass().getClassLoader());
	}
	
	@Test
	public void test2() throws Exception {
		Constructor<ClassA> constructor = EzyClasses.getConstructor(ClassA.class);
		EzyClasses.newInstance(constructor);
		EzyClasses.newInstance(ClassA.class);
	}
	
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void test3() {
		EzyClasses.newInstance("A 1", getClass().getClassLoader());
	}
	
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void test4() {
		EzyClasses.getClass("A 1", getClass().getClassLoader());
	}
	
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void test5() {
		EzyClasses.newInstance(ClassB.class);
	}
	
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void test6() throws Exception {
		Constructor<ClassB> c = ClassB.class.getConstructor(String.class);
		EzyClasses.newInstance(c);
	}
	
	@Test(expectedExceptions = {IllegalArgumentException.class})
	public void test7() {
		EzyClasses.getConstructor(ClassB.class);
	}
	
	@Test
	public void newInstanceTest() {
		Asserts.assertNotNull(EzyClasses.newInstance(
				ClassA.class.getName(), 
				new Class<?>[] {boolean.class},
				new Object[] {true}));
		
		Asserts.assertNotNull(EzyClasses.newInstance(
				ClassA.class.getName(), 
				new Class<?>[] {String.class},
				new Object[] {true}));
	}
	
	@Override
	public Class<?> getTestClass() {
		return EzyClasses.class;
	}
	
	public static class ClassA {
		public ClassA() {}
		
		public ClassA(boolean encrypted) {}
	}
	
	public static class ClassB {
		protected ClassB(int a) {}
		
		public ClassB(String s) {
		}
	}
	
}
