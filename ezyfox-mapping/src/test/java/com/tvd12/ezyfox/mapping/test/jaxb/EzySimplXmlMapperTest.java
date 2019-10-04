package com.tvd12.ezyfox.mapping.test.jaxb;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.mapping.jaxb.EzySimplXmlMapper;

public class EzySimplXmlMapperTest {

	@Test
	public void test() {
		EzySimplXmlMapper mapper = EzySimplXmlMapper.builder()
				.contextClass(getClass())
				.contextPath(getClass().getPackage().getName())
				.classLoader(getClass().getClassLoader())
				.build();
		System.out.println(mapper.read("test-data/person.xml", Person.class));
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void test2() {
		EzySimplXmlMapper.builder()
			.contextPath("a b c")
			.classLoader(getClass().getClassLoader())
			.build();
	}
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void test3() {
		EzySimplXmlMapper mapper = EzySimplXmlMapper.builder()
				.contextClass(getClass())
				.contextPath(getClass().getPackage().getName())
				.classLoader(getClass().getClassLoader())
				.build();
		System.out.println(mapper.read("test-data/person-error.txt", Person.class));
	}
}
