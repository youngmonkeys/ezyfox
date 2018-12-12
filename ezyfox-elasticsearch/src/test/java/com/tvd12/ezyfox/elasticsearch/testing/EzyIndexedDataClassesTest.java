package com.tvd12.ezyfox.elasticsearch.testing;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.elasticsearch.EzyIndexTypes;
import com.tvd12.ezyfox.elasticsearch.EzyIndexedDataClasses;
import com.tvd12.ezyfox.elasticsearch.testing.data.Person1;
import com.tvd12.test.base.BaseTest;

public class EzyIndexedDataClassesTest extends BaseTest {

	@Test
	public void test() {
		EzyIndexedDataClasses object = EzyIndexedDataClasses.builder()
			.scan("com.tvd12.ezyfox.elasticsearch.testing.data")
			.build();
		EzyIndexTypes indexTypes = object.getIndexTypes(Person1.class);
		assertEquals(indexTypes.getIndexes(), Sets.newHashSet("test1", "test2"));
	}
	
}
