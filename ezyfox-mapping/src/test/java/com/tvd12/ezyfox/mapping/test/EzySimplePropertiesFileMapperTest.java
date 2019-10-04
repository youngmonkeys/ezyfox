package com.tvd12.ezyfox.mapping.test;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.mapping.properties.EzyPropertiesFileMapper;
import com.tvd12.ezyfox.mapping.properties.EzySimplePropertiesFileMapper;
import com.tvd12.properties.file.annotation.PropertyWrapper;

import lombok.Getter;
import lombok.Setter;

public class EzySimplePropertiesFileMapperTest {

	@Test
	public void test() {
		EzyPropertiesFileMapper mapper = EzySimplePropertiesFileMapper.builder()
				.context(getClass())
				.build();
		System.out.println(mapper.read("test-data/person.properties", Person.class));
	}
	
	@Getter
	@Setter
	@PropertyWrapper
	public static class Person {
		protected String name;
	}
	
}
