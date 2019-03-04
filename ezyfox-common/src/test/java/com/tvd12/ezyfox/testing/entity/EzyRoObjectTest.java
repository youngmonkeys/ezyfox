package com.tvd12.ezyfox.testing.entity;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.entity.EzyRoObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

public class EzyRoObjectTest {

	@Test
	public void test() {
		EzyRoObject object = EzyEntityFactory.newObjectBuilder()
				.append("a", 1)
				.build();
		assert object.get("a", int.class, 0) == (Object)1;
		assert object.get("aaa", int.class, 0) == (Object)0;
		assert object.getValue("a", int.class, 0) == (Object)1;
		assert object.getValue("aaa", int.class, 0) == (Object)0;
	}
	
}
