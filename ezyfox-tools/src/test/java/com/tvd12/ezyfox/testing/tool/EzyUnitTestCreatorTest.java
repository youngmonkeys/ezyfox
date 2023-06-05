package com.tvd12.ezyfox.testing.tool;

import com.tvd12.ezyfox.tool.EzyBuilderCreator;
import com.tvd12.ezyfox.tool.EzyUnitTestCreator;
import org.testng.annotations.Test;

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

    @Test
    public void createTest() {
        // given
        EzyUnitTestCreator instance = new EzyUnitTestCreator(
            EzyBuilderCreator.class
        );

        // when
        String actual = instance.create("buildBySetter");

        // then
        System.out.println(actual);
    }
}
