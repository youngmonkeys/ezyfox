package com.tvd12.ezyfox.testing.tool;

import com.tvd12.ezyfox.tool.EzyFileClassPackCreator;

public class EzyFileClassPackCreatorTest {

    public static void main(String[] args) {
        EzyFileClassPackCreator creator = new EzyFileClassPackCreator()
                .sourcePath("src/test/java")
                .baseClassName("User")
                .basePackageName("com.tvd12.ezyfox.testing.tool")
                .templateFolderPath("template")
                .add("Repo", "repo", "repo.txt")
                .add("Service", "service", "service.txt")
                .add("ServiceImpl", "service.impl", "service-impl.txt");
        System.out.println(creator.create());
    }

}
