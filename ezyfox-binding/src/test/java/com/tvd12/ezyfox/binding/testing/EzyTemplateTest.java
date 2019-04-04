package com.tvd12.ezyfox.binding.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyTemplate;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;

public class EzyTemplateTest {

	@Test
	public void test() {
		assert (new ClassBTemplate()).getObjectType() == null;
	}
	
	public static class ClassBTemplate extends ClassATemplate {
	}
	
	public static class ClassATemplate implements EzyTemplate<Object, Object> {

		@Override
		public Object read(EzyUnmarshaller unmarshaller, Object value) {
			return null;
		}

		@Override
		public Object write(EzyMarshaller marshaller, Object object) {
			return null;
		}
		
	}
}
