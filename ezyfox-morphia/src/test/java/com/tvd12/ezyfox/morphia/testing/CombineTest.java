package com.tvd12.ezyfox.morphia.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.morphia.testing.data.Cat;
import com.tvd12.ezyfox.morphia.testing.repo.CatRepo;

public class CombineTest extends BaseMongoDBTest {

	@Test
	public void test() {
		CatRepo repo = (CatRepo) BEAN_CONTEXT.getBean(CatRepo.class);
		repo.save(new Cat());
	}
	
	
	
}
