package com.tvd12.ezyfox.testing.reflect;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.test.base.BaseTest;

import lombok.Getter;
import lombok.Setter;

public class EzyClassTest extends BaseTest {
	
	@Test
	public void test() {
		EzyClass clazz = new EzyClass(A.class);
		System.out.println("test classA methods: " + clazz.getMethods());
		assertEquals(clazz.getName(), A.class.getName());
		assertTrue(clazz.isAnnotated(ExampleAnnotation.class));
		ExampleAnnotation ann = clazz.getAnnotation(ExampleAnnotation.class);
		assertNotNull(ann);
		assertTrue(clazz.getPublicMethods().size() == 10);
		assertTrue(clazz.getSetterMethods().size() == 5);
		assertTrue(clazz.getGetterMethods().size() == 5);
		assertTrue(clazz.getPublicMethods(m -> !m.getName().contains("D")).size() == 8);
		assertTrue(clazz.getSetterMethods(m -> !m.getName().contains("D")).size() == 4);
		assertTrue(clazz.getGetterMethods(m -> !m.getName().contains("D")).size() == 4);
		assertTrue(clazz.getGetterMethods(m -> !m.getName().contains("D")).size() == 4);
		assertTrue(clazz.getField(f -> f.getName().equals("a")) != null);
		assertTrue(clazz.getMethod(m -> m.getName().equals("getE")) != null);
		assertTrue(clazz.getPublicMethod(m -> m.getName().equals("getE")) != null);
		System.err.println("size = " + clazz.getMethods(m -> !m.getName().contains("D")).size());
		assertEquals(clazz.getMethods(m -> !m.getName().contains("D")).size(), 9 + 2);
		assertEquals(clazz.getFields().size(), 10 + 2);
		assertEquals(clazz.getWritableFields().size(), 9 + 2);
		assertEquals(clazz.getPublicFields().size(), 4);
		assertEquals(clazz.getPublicFields(f -> !f.getName().equals("a")).size(), 3);
		assertEquals(clazz.getFields(f -> !f.getName().equals("a")).size(), 9 + 2);
		clazz.getMethods(m -> !m.getName().contains("D"), m -> new EzyMethod(m.getMethod()));
		assertTrue(clazz.toString().equals(A.class.toString()));
		assertEquals(clazz.getClazz(), A.class);
		assertEquals(clazz.getMethods().size(), 11 + 2);
		
		assertEquals(clazz.getDeclaredFields().size(), 6 + 1);
		assertEquals(clazz.getDeclaredMethods().size(), 6 + 1);
		System.err.println("fields = " + clazz.getDeclaredFields());
		System.err.println("methods = " + clazz.getDeclaredMethods());
		assert clazz.getField("x") != null;
		clazz.getDeclaredMethods(m -> true);
		clazz.getDeclaredSetterMethods();
		clazz.getDeclaredGetterMethods();
		clazz.getFieldsByName();
		clazz.getMethodsByName();
		clazz.getMethod("setY");
		clazz.newInstance();
		clazz.getDeclaredConstructors();
		clazz.getDeclaredConstructor();
		
		assert clazz.getModifiers() == clazz.getClazz().getModifiers();
		
		EzyClass clazzX = new EzyClass(AX.class);
		assertTrue(clazzX.getAnnotatedMethods(ExampleAnnotation.class).size() == 4);
		assertTrue(clazzX.getAnnotatedMethod(ExampleAnnotation.class).isPresent());
		assertTrue(clazzX.getAnnotatedSetterMethod(ExampleAnnotation.class).isPresent());
		assertTrue(clazzX.getAnnotatedGetterMethod(ExampleAnnotation.class).isPresent());
		assertTrue(clazzX.getAnnotatedFields(ExampleAnnotation.class).size() == 2);
		assertTrue(clazzX.getAnnotatedField(ExampleAnnotation.class).isPresent());
	}
	
	@Test
	public void test2() {
		EzyClass clazz = new EzyClass(A2.class);
		assertEquals(clazz.getGetterMethod(m -> m.isAnnotated(EzyId.class)).get().getName(), "getName");
		assertEquals(clazz.getSetterMethod(m -> m.isAnnotated(EzyId.class)).get().getName(), "setName");
		assertFalse(clazz.getGetterMethod(m -> m.isAnnotated(EzyAutoImpl.class)).isPresent());
		assertFalse(clazz.getSetterMethod(m -> m.isAnnotated(EzyAutoImpl.class)).isPresent());
		
		assertEquals(clazz.getGetterMethod("getName").getName(), "getName");
		assertEquals(clazz.getSetterMethod("setName").getName(), "setName");
		
		assertEquals(clazz.getGetterMethod("getName x"), null);
		assertEquals(clazz.getSetterMethod("setName z"), null);
		
		assert clazz.getPublicMethod(m -> m.getName().equals("getName")).isPresent();
		assert !clazz.getPublicMethod(m -> m.getName().equals("getNameXYZ")).isPresent();
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void test3() {
		new EzyClass(ABC.class).getDeclaredConstructor();
	}
	
	public static class ABC {
		public ABC(String s) {
		}
	}
	
	public static class A2 {
		
		@EzyId
		public String getName() {
			return "name";
		}
		
		@EzyId
		public void setName(String name) {
		}
		
		protected void setNo() {
		}
		
	}
	
	public static class B {
		public String x = "x";
		
		@Setter
		@Getter
		protected String y = "y";
		
		@Getter
		@Setter
		private String z = "z";
		
		protected static final String FINAL_FIELD = "final field";
		
		protected String protectedMethod() {
			return "a";
		}
	}
	
	@ExampleAnnotation
	public static class A extends B {
		
		public String a = "a";
		public String b = "b";
		@ExampleAnnotation
		public String c = "c";
		
		@Setter
		@Getter
		@ExampleAnnotation
		private String d = "d";
		
		@Setter
		@Getter
		@ExampleAnnotation
		private String e = "e";
		
		@Setter
		@Getter
		@ExampleAnnotation
		private String f = "f";
		
	}
	
	public static class AX {
		
		@ExampleAnnotation
		protected String fieldA;
		@ExampleAnnotation
		protected String fieldB;
		protected String fieldC;
		
		@ExampleAnnotation
		protected void methodA() {}
		
		@ExampleAnnotation
		protected void methodB() {}
		
		@ExampleAnnotation
		public String getValue() {
			return "";
		}
		
		@ExampleAnnotation
		public void setValue(String value) {}
	}
	
}
