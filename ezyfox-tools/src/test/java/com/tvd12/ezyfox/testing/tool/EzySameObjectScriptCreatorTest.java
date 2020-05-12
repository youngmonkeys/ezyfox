package com.tvd12.ezyfox.testing.tool;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.tool.EzySameObjectScriptCreator;

import lombok.Getter;
import lombok.Setter;

public class EzySameObjectScriptCreatorTest {

	@Test
	public void test() {
		EzySameObjectScriptCreator creator = new EzySameObjectScriptCreator()
				.originClass(A.class)
				.targetClass(B.class)
				.originObjectName("imA")
				.targetObjectName("imB");
		System.out.println(creator.generateFuncScript());
	}
	
	@Getter
	@Setter
	private static class A  {
		
		private String name;
		private String value;
		
	}
	
	@Getter
	@Setter
	private static class B  {
		
		private String name;
		private String value;
		
	}
	
}
