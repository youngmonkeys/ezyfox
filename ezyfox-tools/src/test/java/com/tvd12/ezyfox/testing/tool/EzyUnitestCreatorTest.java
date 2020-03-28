package com.tvd12.ezyfox.testing.tool;

import com.tvd12.ezyfox.tool.EzyUnitestCreator;

public class EzyUnitestCreatorTest {

	public void test() {
		EzyUnitestCreator tool = new EzyUnitestCreator(ForUnitestTool.class);
		tool.setProjectPath(".");
		tool.setTestPackage("com.tvd12.ezyfox.testing.tool");
//		EzyUnitestCreator tool = new EzyUnitestCreator(ForUnitestTool2.class);
		System.out.println(tool.createToFile("func1"));
	}
	
	public static void main(String[] args) {
		EzyUnitestCreatorTest test = new EzyUnitestCreatorTest();
		test.test();
	}
	
}
