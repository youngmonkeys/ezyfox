package com.tvd12.ezyfox.testing.reflect;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.reflect.EzyReflection;
import com.tvd12.ezyfox.reflect.EzyReflectionProxy;
import com.tvd12.test.base.BaseTest;

public class EzyReflectionProxyTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		new EzyReflectionProxy("com.tvd12.ezyfox.testing.reflect");
		new EzyReflectionProxy(Sets.newHashSet("com.tvd12.ezyfox.testing.reflect"));
		new EzyReflectionProxy(Lists.newArrayList("com.tvd12.ezyfox.testing.reflect"));
		new EzyReflectionProxy("com.tvd12.ezyfox.testing.reflect", getClass().getClassLoader());
		new EzyReflectionProxy(Sets.newHashSet("com.tvd12.ezyfox.testing.reflect"), getClass().getClassLoader());
		new EzyReflectionProxy(Lists.newArrayList("com.tvd12.ezyfox.testing.reflect"), getClass().getClassLoader());
		EzyReflection reflection = new EzyReflectionProxy("com.tvd12.ezyfox.testing.reflect");
		reflection.getExtendsClasses(BaseTest.class);
		reflection.getAnnotatedClasses(ExampleAnnotation.class);
		assert reflection.getAnnotatedClasses(ExampleAnnotation.class).size() == reflection.getAnnotatedClasses(Sets.newHashSet(ExampleAnnotation.class)).size();
	}
	
}
