package com.tvd12.ezyfox.testing.tool;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.tool.EzyStringTool;

public class EzyStringToolTest {

	@Test
	public void test() {
		assert EzyStringTool.tab("a", 2).equals("\t\ta");
		assert EzyStringTool.tab("a", 2, true).equals("        a");
		
		String func = "public void hello() {\n" +
				"\tSystem.out.print(hello);\n" +
				"}";
		System.out.println(EzyStringTool.tabAll(func, 2));
		System.out.println(EzyStringTool.tabAll(func, 2, true));
	}
	
}
