package com.tvd12.ezyfox.binding.testing;

import java.util.Collection;
import java.util.Map;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.impl.EzyObjectReaderBuilder;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.test.base.BaseTest;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class EzyObjectReaderBuilderTest extends BaseTest {

	@Test
	public void test() {
		EzyObjectReaderBuilder.setDebug(true);
		EzyObjectReaderBuilder builder = new EzyObjectReaderBuilder(new EzyClass(ClassA.class));
		builder.build();
	}
	
	@Test
	public void testConstructorCase() {
		EzyObjectReaderBuilder.setDebug(true);
		EzyObjectReaderBuilder builder = new EzyObjectReaderBuilder(new EzyClass(ClassB.class));
		builder.build();
	}
	
	@Test
	public void testConstructorNoFieldsCase() {
		EzyObjectReaderBuilder.setDebug(true);
		EzyObjectReaderBuilder builder = new EzyObjectReaderBuilder(new EzyClass(ClassC.class));
		builder.build();
	}

	@SuppressWarnings("rawtypes")
	public static class ClassA {
		public Map map;
		public void setCollection(Collection collection) {
		}
	}
	
	@Getter
	@AllArgsConstructor
	public static class ClassB {
		private String name;
		private String value;
	}
	
	@Getter
	public static class ClassC {
		public ClassC(
				boolean booleanValue,
				byte byteValue,
				char charValue,
				double doubleValue,
				float floatValue,
				int intValue,
				long longValue,
				short shortValue,
				String stringValue) {}
	}
	
}
