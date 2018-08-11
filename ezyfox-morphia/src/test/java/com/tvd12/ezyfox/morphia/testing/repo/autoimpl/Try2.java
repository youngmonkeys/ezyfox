package com.tvd12.ezyfox.morphia.testing.repo.autoimpl;

import com.tvd12.ezyfox.morphia.testing.BaseMongoDBTest;
import com.tvd12.ezyfox.morphia.testing.data.Monkey;
import com.tvd12.ezyfox.morphia.testing.service.CatChickendService;

public class Try2 extends BaseMongoDBTest {
	
	public static void main(String[] args) throws Exception {
		CatChickendService service = 
				(CatChickendService) BEAN_CONTEXT.getBean(CatChickendService.class);
		service.printAllCatAndChickend();
		service.save2Monkey(new Monkey(), new Monkey());
	}
	
}
