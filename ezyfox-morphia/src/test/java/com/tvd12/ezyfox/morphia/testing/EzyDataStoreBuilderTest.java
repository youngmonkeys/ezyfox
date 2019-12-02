package com.tvd12.ezyfox.morphia.testing;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.morphia.EzyDataStoreBuilder;
import com.tvd12.ezyfox.morphia.testing.data.Cat;
import com.tvd12.ezyfox.morphia.testing.data.Person;

public class EzyDataStoreBuilderTest extends BaseMongoDBTest {
	
	@Test
	public void test() {
		DATASTORE.save(new Cat());
		DATASTORE.save(new Person());
		
		EzyDataStoreBuilder.dataStoreBuilder()
			.mongoClient(MONGO_CLIENT)
			.databaseName("test")
			.addEntityClasses(Pig.class, Duck.class)
			.build();
	}


}
