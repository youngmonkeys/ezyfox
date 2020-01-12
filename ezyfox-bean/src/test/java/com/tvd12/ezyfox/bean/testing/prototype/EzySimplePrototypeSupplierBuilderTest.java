package com.tvd12.ezyfox.bean.testing.prototype;

import java.util.List;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.asm.EzyInstruction;
import com.tvd12.ezyfox.asm.EzyFunction.EzyBody;
import com.tvd12.ezyfox.bean.impl.EzyBeanNameParser;
import com.tvd12.ezyfox.bean.impl.EzyByConstructorPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.impl.EzySimplePrototypeFactory;
import com.tvd12.ezyfox.reflect.EzyClass;
import com.tvd12.test.base.BaseTest;

public class EzySimplePrototypeSupplierBuilderTest extends BaseTest {

	@Test(expectedExceptions = {IllegalStateException.class})
	public void test() {
		new ExSimplePrototypeSupplierBuilder(
				new EzyClass(ClassA.class)).load(new EzySimplePrototypeFactory());
	}
	
	public static class ExSimplePrototypeSupplierBuilder extends EzyByConstructorPrototypeSupplierLoader {

		public ExSimplePrototypeSupplierBuilder(EzyClass clazz) {
			super(EzyBeanNameParser.getBeanName(clazz.getClazz()), clazz);
		}
		
		@Override
		protected EzyInstruction newConstructInstruction(EzyBody body, List<String> cparams) {
			throw new RuntimeException();
		}
		
	}
	
}
