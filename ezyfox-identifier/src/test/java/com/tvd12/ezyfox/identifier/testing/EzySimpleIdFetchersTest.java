package com.tvd12.ezyfox.identifier.testing;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.identifier.EzyIdFetcher;
import com.tvd12.ezyfox.identifier.EzyIdFetchers;
import com.tvd12.ezyfox.identifier.EzySimpleIdFetcherImplementer;
import com.tvd12.ezyfox.identifier.EzySimpleIdFetchers;
import com.tvd12.ezyfox.identifier.testing.annotation.HasIdTest;
import com.tvd12.ezyfox.identifier.testing.entity1.Message;
import com.tvd12.ezyfox.identifier.testing.entity1.Message2;
import com.tvd12.ezyfox.identifier.testing.entity1.Message3;
import com.tvd12.ezyfox.identifier.testing.entity1.Message4;
import com.tvd12.ezyfox.identifier.testing.entity1.Message5;
import com.tvd12.ezyfox.identifier.testing.entity2.Message6;
import com.tvd12.ezyfox.identifier.testing.entity2.NotMessage;
import com.tvd12.ezyfox.identifier.testing.entity3.Message7;
import com.tvd12.ezyfox.identifier.testing.entity3.Message8;
import com.tvd12.ezyfox.identifier.testing.entity3.Message9;
import com.tvd12.ezyfox.util.EzyMapBuilder;
import com.tvd12.test.base.BaseTest;

public class EzySimpleIdFetchersTest extends BaseTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		EzySimpleIdFetcherImplementer.setDebug(true);
		EzyIdFetchers idFetchersObject = new EzySimpleIdFetchers.Builder() {
				protected Set<Class<? extends Annotation>> getAnnotationClasses() {
					return Sets.newHashSet(HasIdTest.class);
				};
			}
			.scan("com.tvd12.ezyfox.identifier.testing.entity1")
			.scan("com.tvd12.ezyfox.identifier.testing.entity1", "com.tvd12.ezyfox.identifier.testing.entity2")
			.scan(Sets.newHashSet("com.tvd12.ezyfox.identifier.testing.entity2"))
			.addClass(Message.class)
			.addClass(Message2.class)
			.addClasses(Message3.class, Message4.class)
			.addClasses(Sets.newHashSet(Message5.class, Message6.class))
			.addIdFetcher(Message3.class, m3 -> ((Message3)m3).getId())
			.addIdFetchers(EzyMapBuilder.mapBuilder()
					.put(Message6.class, new EzyIdFetcher() {
						@Override
						public Object getId(Object object) {
							return ((Message6)object).id;
						}
					})
					.build())
			.build();
		Map<Class<?>, EzyIdFetcher> idFetchers = idFetchersObject.getIdFetchers();
		EzyIdFetcher idFetcher = idFetchers.get(Message2.class);
		
		assert idFetcher.getId(new Message2(100L, "hello")).equals(100L);
		assert idFetchersObject.getIdFetcher(Message6.class) != null;
		try {
			idFetchersObject.getIdFetcher(NotMessage.class);
		}
		catch (Exception e) {
			assert e instanceof IllegalArgumentException;
		}
		
		idFetchers = EzySimpleIdFetchers.builder()
				.build()
				.getIdFetchers();
		assert idFetchers.isEmpty();
		idFetchers = new EzySimpleIdFetchers.Builder()
				.scan("com.tvd12.ezyfox.identifier.testing.entity2")
				.build()
				.getIdFetchers();
		assert idFetchers.size() == 0;
		
		try {
			idFetchersObject = new EzySimpleIdFetchers.Builder() {
				protected Set<Class<? extends Annotation>> getAnnotationClasses() {
					return Sets.newHashSet(HasIdTest.class);
				};
			}
			.addClass(Message7.class)
			.build();
		}
		catch (Exception e) {
			assert e instanceof IllegalStateException;
		}
		
		try {
			idFetchersObject = new EzySimpleIdFetchers.Builder() {
				protected Set<Class<? extends Annotation>> getAnnotationClasses() {
					return Sets.newHashSet(HasIdTest.class);
				};
			}
			.addClass(Message8.class)
			.build();
		}
		catch (Exception e) {
			assert e instanceof IllegalStateException;
		}
		EzySimpleIdFetcherImplementer.setDebug(false);
		idFetchersObject = new EzySimpleIdFetchers.Builder() {
			protected Set<Class<? extends Annotation>> getAnnotationClasses() {
				return Sets.newHashSet(HasIdTest.class);
			};
		}
		.addClass(Message9.class)
		.build();
	}
	
}
