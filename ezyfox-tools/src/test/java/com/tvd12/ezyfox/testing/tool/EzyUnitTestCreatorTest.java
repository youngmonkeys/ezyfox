package com.tvd12.ezyfox.testing.tool;

import com.tvd12.ezyfox.tool.EzyUnitTestCreator;

public class EzyUnitTestCreatorTest {

    public static void main(String[] args) {
        EzyUnitTestCreatorTest test = new EzyUnitTestCreatorTest();
        test.test();
    }

    public void test() {
        EzyUnitTestCreator tool = new EzyUnitTestCreator(ForUnitTestTool.class);
        tool.setProjectPath(".");
        tool.setTestPackage("com.tvd12.ezyfox.testing.tool");
        System.out.println(tool.createToFile("func1"));
    }
}
