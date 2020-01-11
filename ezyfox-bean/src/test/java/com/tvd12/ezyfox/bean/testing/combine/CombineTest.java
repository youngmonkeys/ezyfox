package com.tvd12.ezyfox.bean.testing.combine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;
import com.tvd12.ezyfox.bean.EzyPrototypeFactory;
import com.tvd12.ezyfox.bean.EzyPrototypeSupplier;
import com.tvd12.ezyfox.bean.EzySingletonFactory;
import com.tvd12.ezyfox.bean.impl.EzyByConstructorPrototypeSupplierLoader;
import com.tvd12.ezyfox.bean.testing.combine.pack1.ClassA1;
import com.tvd12.ezyfox.bean.testing.combine.pack1.Singleton1;
import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.reflect.EzyReflectionProxy;

public class CombineTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		EzyByConstructorPrototypeSupplierLoader.setDebug(true);
		EzyBeanContextBuilder builder = EzyBeanContext.builder()
				.scan(
						"com.tvd12.ezyfox.bean.testing.combine.pack0",
						"com.tvd12.ezyfox.bean.testing.combine.pack1",
						"com.tvd12.ezyfox.bean.testing.combine.pack2"
				)
				.scan(new ArrayList<>())
				.scan(new Iterable<String>() {
					
					@Override
					public Iterator<String> iterator() {
						return Lists.newArrayList("com.tvd12.ezyfox.bean.testing.combine.pack2").iterator();
					}
				})
				.addAllClasses(new Object())
				.addAllClasses(new EzyReflectionProxy("com.tvd12.ezyfox.bean.testing.combine.pack2"))
				.addProperty("hello", "world")
				.addProperty("foo", "bar")
				.addProperty("array", "1,2,3,4,5")
				.addProperty("ints", Lists.newArrayList(1, 2, 3))
				.addSingleton("list", new ArrayList<>())
				.addSingleton("map", new HashMap<>())
				.addSingletonClasses(new Class[] {
						SingletonX1.class, 
						SingletonX2.class,
						Singleton12.class
				})
				.addPrototypeClasses(new Class[] {
						ClassA12.class
				})
				.addSingleton("singleton2", new SingletonX3())
				.addConfigurationBeforeClasses(V111ConfigurationBefore01.class, V111ConfigurationBefore01.class)
				.addConfigurationBeforeClasses(Sets.newHashSet(V111ConfigurationBefore01.class, V111ConfigurationBefore01.class))
				.addConfigurationClasses(V111Configuration01.class, V111Configuration01.class)
				.addConfigurationClasses(Sets.newHashSet(V111Configuration01.class, V111Configuration01.class));
		EzyBeanContext context = builder.build();
		SingletonX1 x1 = (SingletonX1) context.getBean("singletonX1", SingletonX1.class);
		SingletonX2 x2 = (SingletonX2) context.getBean("singletonX2", SingletonX2.class);
		
		assert V111ISingleton01.class.isAssignableFrom(V111ISingleton01.class);
		assert !V111Singleton01Impl01.class.isAssignableFrom(V111ISingleton01.class);
		
		assert x1.getSingletonX1() == x1;
		assert x1.getSingletonX2() == x2;
		assert x2.getSingletonX1() == x1;
		assert x2.getSingletonX2() == x2;
		assert context.getBean(V111Singleton01.class) != null;
		assert context.getSingleton(V111ISingleton01.class) != null;
		assert context.getSingleton(V111Singleton01Impl01.class) == null;
		assert context.getBean(V111Singleton02.class) != null;
		assert context.getBean(V111Prototype01.class) != null;
		assert context.getBean(V111IPrototype01.class) != null;
		try {
			assert context.getBean(V111Prototype01Impl01.class) == null;
		}
		catch (Exception e) {
			assert e instanceof IllegalArgumentException;
		}
		
		Singleton1 singleton1 = (Singleton1) context.getBean("s1", Singleton1.class);
		assert singleton1 != null;
		assert singleton1.getMap() != null;
		
		ClassA12 a121 = (ClassA12) context.getBean("classA12", ClassA12.class);
		ClassA12 a122 = (ClassA12) context.getBean("classA12", ClassA12.class);
		assert a121 != a122;

		EzySingletonFactory singletonFactory = context.getSingletonFactory();
		EzyPrototypeFactory prototypeFactory = context.getPrototypeFactory();
		
		EzyPrototypeSupplier a1Supplier = prototypeFactory.getSupplier("a1", ClassA1.class);
		prototypeFactory.getProperties(a1Supplier);
		
		Object s1Singleton = singletonFactory.getSingleton("s1", Object.class);
		singletonFactory.getProperties(s1Singleton);
		
		List<Object> singletons = singletonFactory.getSingletons(EzyCombine0Ann.class);
		assert singletons.size() == 2;
		
		List<EzyPrototypeSupplier> prototypeSuppliers = prototypeFactory.getSuppliers(EzyCombine0Ann.class);
		assert prototypeSuppliers.size() == 2;
	}
	
}
