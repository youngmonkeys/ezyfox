package com.tvd12.ezyfox.identifier.testing;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

import com.google.common.collect.Sets;
import com.tvd12.ezyfox.identifier.EzyIdSetter;
import com.tvd12.ezyfox.identifier.EzySimpleIdSetterImplementer;
import com.tvd12.ezyfox.identifier.EzySimpleIdSetters;
import com.tvd12.ezyfox.identifier.testing.annotation.HasIdTest;
import com.tvd12.ezyfox.identifier.testing.entity.Message2;
import com.tvd12.ezyfox.identifier.testing.entity.Message3;
import com.tvd12.test.base.BaseTest;

public class EzySimpleIdSettersTest extends BaseTest {

	@Test
	public void test() {
		EzySimpleIdSetterImplementer.setDebug(true);
		Map<Class<?>, EzyIdSetter> idSetters = new EzySimpleIdSetters.Builder() {
				@SuppressWarnings("unchecked")
				protected Set<Class<? extends Annotation>> getAnnotationClasses() {
					return Sets.newHashSet(HasIdTest.class);
				};
			}
			.scan("com.tvd12.ezyfox.identifier.testing.entity")
			.addIdSetter(Message3.class, (m3,id) -> ((Message3)m3).setId((Long)id))
			.build()
			.getIdSetters();
		EzyIdSetter idSetter = idSetters.get(Message2.class);
		idSetter.setId(new Message2(10L, "x"), 100L);
	}
	
}
