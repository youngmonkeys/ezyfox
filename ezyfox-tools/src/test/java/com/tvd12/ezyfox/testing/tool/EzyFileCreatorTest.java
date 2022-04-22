package com.tvd12.ezyfox.testing.tool;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.tool.EzyFileCreator;

public class EzyFileCreatorTest {

    @Test
    public void test() {
        EzyFileCreator fileCreator = new EzyFileCreator()
                .template("${hello#or#world#or#galaxy} == ${} $ { } ${foo} ${yes#or#no} ${d")
                .replace("world", "Hello World")
                .replace("foo", "I'm bar")
                .replace("no", "I'm yes");
        String content = fileCreator.createContent();
        System.out.println(content);
    }
}