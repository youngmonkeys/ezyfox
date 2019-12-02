package com.tvd12.ezyfox.mongodb.testing.bean;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.mongodb.bean.EzySimpleRepositoryImplementer;
import com.tvd12.test.base.BaseTest;

public class EzySimpleRepositoryImplementerTest extends BaseTest {

	@Test(expectedExceptions = IllegalStateException.class)
	public void test() {
		ExEzySimpleRepositoryImplementer implementer = new ExEzySimpleRepositoryImplementer(PersonRepo.class);
		implementer.implement(new MongoTemplate());
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
			throw new IllegalStateException("has no super class");
		}
		
	}
	
}
