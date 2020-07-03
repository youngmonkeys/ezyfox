package com.tvd12.ezyfox.testing.reflect;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.ezyfox.reflect.EzyGetterBuilder;
import com.tvd12.ezyfox.reflect.EzyMethod;
import com.tvd12.ezyfox.reflect.EzyObjectProxy;
import com.tvd12.ezyfox.reflect.EzyObjectProxyProvider;
import com.tvd12.ezyfox.reflect.EzySetterBuilder;
import com.tvd12.ezyfox.reflect.EzyObjectProxy.Builder;

import lombok.Getter;
import lombok.Setter;

public class EzyObjectProxyProviderTest {

	@Test
	public void test() {
		EzyGetterBuilder.setDebug(true);
		EzySetterBuilder.setDebug(true);
		EzyObjectProxyProvider provider = new EzyObjectProxyProvider();
		EzyObjectProxy objectProxy = provider.getObjectProxy(A.class);
		A a = new A();
		objectProxy.setProperty(a, "id", 10);
		objectProxy.setProperty(a, "a", true);
		objectProxy.setProperty(a, "b", (byte)1);
		objectProxy.setProperty(a, "c", 'a');
		objectProxy.setProperty(a, "d", 11D);
		objectProxy.setProperty(a, "e", 12F);
		objectProxy.setProperty(a, "f", 15L);
		objectProxy.setProperty(a, "g", (short)16);
		objectProxy.setProperty(a, "value", "dzung");
		System.out.println((int)objectProxy.getProperty(a, "id"));
		System.out.println((boolean)objectProxy.getProperty(a, "a"));
		System.out.println((byte)objectProxy.getProperty(a, "b"));
		System.out.println((char)objectProxy.getProperty(a, "c"));
		System.out.println((double)objectProxy.getProperty(a, "d"));
		System.out.println((float)objectProxy.getProperty(a, "e"));
		System.out.println((long)objectProxy.getProperty(a, "f"));
		System.out.println((short)objectProxy.getProperty(a, "g"));
		System.out.println((String)objectProxy.getProperty(a, "value"));
		assert objectProxy.getPropertyType("id") == int.class;
		assert objectProxy.getProperty(a, "no one") == null;
		objectProxy.setProperty(a, "no one", "no one");
		
		EzyGetterBuilder.setDebug(false);
		EzySetterBuilder.setDebug(false);
		
		EzyObjectProxy aProxy = EzyObjectProxy.builder()
				.propertyKey("_id", "id")
				.addPropertyType("id", int.class)
				.addGetter("id", o ->  {
					return ((A)o).getId();
				})
				.addSetter("id", (o, v) -> {
					((A)o).setId((int)v);
				})
				.build();
		A aa = new A();
		assert aProxy.getPropertyName("_id").equals("id");
		assert aProxy.getPropertyType("_id") == int.class;
		aProxy.setProperty(aa, "_id", 100);
		assert aProxy.getProperty(aa, "_id").equals(100);
		
		provider = new TestObjectProxyProvider();
		objectProxy = provider.getObjectProxy(A.class);
		a = new A();
		objectProxy.setProperty(a, "email", "foo@bar");
		assert objectProxy.getProperty(a, "email").equals("foo@bar");
		System.out.println("email: " + objectProxy.getProperty(a, "email"));
	}
	
	public static void main(String[] args) {
		new EzyObjectProxyProviderTest().test();
	}
	
	public static class TestObjectProxyProvider extends EzyObjectProxyProvider {
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		@Override
		protected void preBuildObjectProxy(EzyClass clazz, Builder builder) {
			List<EzyMethod> methods = clazz.getAnnotatedMethods(EzyId.class);
			for(EzyMethod method : methods) {
				if(method.isGetter()) {
					Function getter = new EzyGetterBuilder()
						.method(method)
						.build();
					builder.addGetter(method.getFieldName(), getter);
				}
				else if(method.isSetter()) {
					BiConsumer setter = new EzySetterBuilder()
							.method(method)
							.build();
					builder.addSetter(method.getFieldName(), setter);
				}
			}
		}
		
	}
	
	@Getter
	@Setter
	public static class A {
		private String em;
		private boolean a;
		private byte b;
		private char c;
		private double d;
		private float e;
		private int id;
		private long f;
		private short g;
		protected String value;
		protected final String finalField = "";
		
		@EzyId
		public void setEmail(String email) {
			this.em = email;
		}
		
		@EzyId
		public String getEmail() {
			return this.em;
		}
	}
	
}
