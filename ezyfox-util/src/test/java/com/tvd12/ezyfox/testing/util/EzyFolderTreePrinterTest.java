package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyFolderTreePrinter;
import org.testng.annotations.Test;

import java.io.File;

public class EzyFolderTreePrinterTest {

    @Test
    public void test() {
        String tree = EzyFolderTreePrinter.builder()
            .tabSize(3)
            .pretty(true)
            .printFile(true)
            .horizontalSymbol(".")
            .verticalSymbol("!")
            .intersectionSymbol("*")
            .build()
            .print(new File("tree-for-test"));
        System.out.println(tree);
    }

    @Test
    public void test2() {
        String tree = EzyFolderTreePrinter.builder()
            .tabSize(3)
            .pretty(false)
            .printFile(false)
            .horizontalSymbol(".")
            .verticalSymbol("!")
            .intersectionSymbol("*")
            .build()
            .print(new File("tree-for-test"));
        System.out.println(tree);
    }

    @Test
    public void test3() {
        String tree = EzyFolderTreePrinter.builder()
            .tabSize(3)
            .pretty(false)
            .printFile(false)
            .horizontalSymbol(".")
            .verticalSymbol("!")
            .intersectionSymbol("*")
            .build()
            .print(new File("tree-for-test/a.txt"));
        System.out.println(tree);
    }
}
