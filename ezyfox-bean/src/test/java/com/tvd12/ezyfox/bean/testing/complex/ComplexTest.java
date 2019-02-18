package com.tvd12.ezyfox.bean.testing.complex;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyErrorHandler;

public class ComplexTest {

	@Test
	public void test() {
		EzyBeanContext.builder()
				.addSingletonClass(ClassAImpl.class)
				.addSingletonClass(ClassBImpl.class)
				.addSingletonClass(ClassCImpl.class)
				.addSingletonClass(ClassDImpl.class)
				.addSingletonClass(ClassEImpl.class)
				.addSingletonClass(ClassFImpl.class)
				.addSingletonClass(ClassGImpl.class)
				.addSingletonClass(ClassHImpl.class)
				.errorHandler(new EzyErrorHandler() {
					
					@Override
					public void handle(Throwable error) {
						throw new RuntimeException(error);
					}
				})
				.build();
	}
	
}
