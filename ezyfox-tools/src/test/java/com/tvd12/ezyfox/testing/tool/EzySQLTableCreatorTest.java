package com.tvd12.ezyfox.testing.tool;

import com.tvd12.ezyfox.testing.tool.entity.*;
import com.tvd12.ezyfox.tool.EzySQLTableCreator;
import com.tvd12.ezyfox.tool.data.EzyCaseType;
import org.testng.annotations.Test;

public class EzySQLTableCreatorTest {

    @Test
    public void test() {
        EzySQLTableCreator creator = new EzySQLTableCreator(UserEntity.class, EzyCaseType.NORMAL, true);
        System.out.println(creator.createScript());
        System.out.println("\n");
        EzySQLTableCreator creator2 = new EzySQLTableCreator(PersonEntity.class, EzyCaseType.UPPERCASE, true);
        System.out.println(creator2.createScript());
        System.out.println("\n");
        EzySQLTableCreator creator3 = new EzySQLTableCreator(PersonEntity.class, EzyCaseType.DASH, true);
        System.out.println(creator3.createScript());
        System.out.println("\n");
        EzySQLTableCreator creator4 = new EzySQLTableCreator(PersonEntity.class, EzyCaseType.LOWERCASE, true);
        System.out.println(creator4.createScript());
        System.out.println("\n");
        EzySQLTableCreator creator5 = new EzySQLTableCreator(PersonEntity.class, EzyCaseType.UNDERSCORE, true);
        System.out.println(creator5.createScript());
        System.out.println("\n");
        EzySQLTableCreator creator6 = new EzySQLTableCreator(PersonEntity.class, EzyCaseType.NORMAL, true);
        System.out.println(creator6.createScript());
        System.out.println("\n");
        EzySQLTableCreator creator7 = new EzySQLTableCreator(PersonEntity.class, EzyCaseType.CAMEL, true);
        System.out.println(creator7.createScript());
        System.out.println("\n");
        EzySQLTableCreator creator8 = new EzySQLTableCreator(AnimalEntity.class, EzyCaseType.CAMEL, true);
        System.out.println(creator8.createScript());
        System.out.println("\n");
        EzySQLTableCreator creator9 = new EzySQLTableCreator(BookEntity.class, EzyCaseType.CAMEL, true);
        System.out.println(creator9.createScript());
        System.out.println("\n");
        EzySQLTableCreator creator10 = new EzySQLTableCreator(CategoryEntity.class, EzyCaseType.CAMEL, true);
        System.out.println(creator10.createScriptToFolder("test-output"));
        System.out.println("\n");
        EzySQLTableCreator creator11 = new EzySQLTableCreator(NoIdEntity.class, EzyCaseType.CAMEL, true);
        System.out.println(creator11.createScriptToFile("test-output/NoIdEntity.sql"));
    }
}
