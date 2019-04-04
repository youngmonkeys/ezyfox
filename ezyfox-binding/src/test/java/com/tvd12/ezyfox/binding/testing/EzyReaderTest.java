package com.tvd12.ezyfox.binding.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.annotation.EzyTemplateImpl;
import com.tvd12.ezyfox.entity.EzyObject;

public class EzyReaderTest {

	@Test
	public void test() {
		assert new ClassAReader().getObjectType() == ClassA.class;
		assert new ClassATemplate().getObjectType() == ClassA.class;
	}
	
	public static class ClassA {
	}
	
	public static class ClassAReader implements EzyReader<EzyObject, ClassA> {

		@Override
		public ClassA read(EzyUnmarshaller unmarshaller, EzyObject value) {
			return null;
		}
	}
	
	@EzyTemplateImpl
	public static class ClassATemplate implements EzyReader<EzyObject, ClassA> {

		@Override
		public ClassA read(EzyUnmarshaller unmarshaller, EzyObject value) {
			return null;
		}

	}
	
}
