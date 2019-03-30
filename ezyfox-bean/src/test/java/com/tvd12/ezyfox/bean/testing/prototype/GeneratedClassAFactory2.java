package com.tvd12.ezyfox.bean.testing.prototype;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;
import com.tvd12.ezyfox.bean.impl.EzyByConstructorPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.impl.EzyByFieldPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.impl.EzyByMethodPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.impl.EzyPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.impl.EzySimpleBeanContext;
import com.tvd12.ezyfox.bean.impl.EzySimplePrototypeFactory;
import com.tvd12.ezyfox.reflect.EzyClass;

public class GeneratedClassAFactory2 {

	@Test
	public void test() throws Exception {
		EzyByConstructorPrototypeSupplierLoader.setDebug(true);
		EzyByFieldPrototypeSupplierLoader.setDebug(true);
		EzyByMethodPrototypeSupplierLoader.setDebug(true);
		
		EzyPrototypeSupplierLoader builder = 
				new EzyByConstructorPrototypeSupplierLoader(new EzyClass(ClassA.class));
		
		EzyPrototypeSupplier supplier = builder.load(new EzySimplePrototypeFactory());
		
		EzyBeanContext context = EzySimpleBeanContext.builder()
				.addProperty("game.auto_start", "true")
				.addProperty("game.byte", "1")
				.addProperty("game.char", "a")
				.addProperty("game.double", "2.0")
				.addProperty("game.float", "3.0")
				.addProperty("game.int", "4")
				.addProperty("game.long", "5")
				.addProperty("game.short", "6")
				.scan("com.tvd12.ezyfox.bean.testing.prototype")
				.build();
		System.out.println(supplier.getObjectType());
		ClassA classA = (ClassA) supplier.supply(context);
		System.out.println(classA);
		System.out.println(classA.getClassB());
		classA = (ClassA) supplier.supply(context);
		System.out.println(classA.getClassB());
	}
	
}
