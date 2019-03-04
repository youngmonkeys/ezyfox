package com.tvd12.ezyfox.testing.io;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.builder.EzyArrayBuilder;
import com.tvd12.ezyfox.collect.Lists;
import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

public class ArrayCollectionToObjectArrayTest {

	@Test
	public void test() {
		EzyArrayBuilder builder = EzyEntityFactory.newArrayBuilder()
				.append(Lists.newArrayList(1, 2, 3))
				.append(EzyEntityFactory.newArrayBuilder()
						.append(4, 5, 6)
						.build());
		EzyArray array = builder.build();
		System.out.println(array.get(0, int[].class));
		System.out.println(array.get(1, byte[].class));
		System.out.println(array.get(1, int[].class));
	}
	
}
