package com.tvd12.ezyfox.json.testing;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.jackson.JacksonObjectMapperBuilder;

public class JacksonObjectMapperBuilderTest {

	@Test
	public void test() throws Exception {
		JacksonObjectMapperBuilder builder = JacksonObjectMapperBuilder.newInstance();
		ObjectMapper mapper = builder.build();
		System.out.print(mapper.writeValueAsString(EzyEntityFactory.newArrayBuilder().append(1, 2, 3).build()));
		System.out.print(mapper.writeValueAsString(EzyEntityFactory.newObjectBuilder().append("hello", "word").build()));
	}
	
}
