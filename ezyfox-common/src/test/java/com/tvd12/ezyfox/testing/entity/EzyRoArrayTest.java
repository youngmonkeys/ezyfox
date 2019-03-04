package com.tvd12.ezyfox.testing.entity;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

public class EzyRoArrayTest {

	@Test
	public void test() {
		EzyArray array = EzyEntityFactory.newArrayBuilder()
				.append(1)
				.append(2)
				.build();
		assert array.getWithDefault(0, 0) == 1;
		assert array.getWithDefault(3, 0) == 0;
		assert array.get(1, int.class, 0) == 2;
		assert array.get(3, int.class, 0) == 0;
		assert array.getValue(0, int.class, 0) == (Object)1;
		assert array.getValue(3, int.class, 0) == (Object)0;
		array.add(Lists.newArrayList(2, 3));
	}
	
}
