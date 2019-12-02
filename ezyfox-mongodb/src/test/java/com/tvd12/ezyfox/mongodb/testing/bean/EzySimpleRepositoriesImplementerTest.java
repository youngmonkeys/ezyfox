package com.tvd12.ezyfox.mongodb.testing.bean;

import java.util.Map;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.mongodb.bean.EzyRepositoriesImplementer;
import com.tvd12.ezyfox.mongodb.bean.EzySimpleRepositoriesImplementer;
import com.tvd12.ezyfox.mongodb.bean.EzySimpleRepositoryImplementer;
import com.tvd12.test.base.BaseTest;

public class EzySimpleRepositoriesImplementerTest extends BaseTest {

	@Test
	public void test() {
		EzyRepositoriesImplementer implementer = new ExEzySimpleRepositoriesImplementer()
				.scan("com.tvd12.ezyfox.mongodb.testing.bean")
				.scan("com.tvd12.ezyfox.mongodb.testing.bean", "com.tvd12.ezyfox.mongodb.testing.bean")
				.scan(Sets.newHashSet("com.tvd12.ezyfox.mongodb.testing.bean"))
				.repositoryInterface(PersonRepo2.class)
				.repositoryInterface(Class.class)
				.repositoryInterface(NothingInterface.class)
				.repositoryInterfaces(PersonRepo2.class, PersonRepo2.class)
				.repositoryInterfaces(Sets.newHashSet(PersonRepo2.class));
		
		MongoTemplate template = new MongoTemplate();
		Map<Class<?>, Object> repos = implementer.implement(template);
		System.out.println("repos: " + repos);
		assert repos.size() == 2;
		
		implementer = new ExEzySimpleRepositoriesImplementer();
		repos = implementer.implement(template);
		assert repos.isEmpty();
	}
	
	public static class ExEzySimpleRepositoriesImplementer extends EzySimpleRepositoriesImplementer {

		@Override
		protected EzySimpleRepositoryImplementer newRepoImplementer(Class<?> itf) {
			return new ExEzySimpleRepositoryImplementer(itf);
		}
		
	}
	
	public static class ExEzySimpleRepositoryImplementer extends EzySimpleRepositoryImplementer {

		public ExEzySimpleRepositoryImplementer(Class<?> clazz) {
			super(clazz);
		}

		@Override
		protected void setRepoComponent(Object repo, Object template) {
		}

		@Override
		protected Class<?> getSuperClass() {
			return ExEzyMongoRepository.class;
		}
		
	}
}
