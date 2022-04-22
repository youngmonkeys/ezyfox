package com.tvd12.ezyfox.testing.util;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.util.EzyDirectories;
import com.tvd12.ezyfox.util.EzyFileUtil;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;

public class EzyDirectoriesTest extends BaseTest {

    private EzyDirectories directories;

    public EzyDirectoriesTest() throws IOException {
        super();
        this.directories = new EzyDirectories().directory(createDirectory());
    }

    private File createDirectory() throws IOException {
        File file = new File("util-directories-test");
        file.mkdirs();
        File a = new File(file.getAbsolutePath() + File.separator + "A");
        File b = new File(file.getAbsolutePath() + File.separator + "B");
        File c = new File(file.getAbsolutePath() + File.separator + "C");
        a.mkdirs();
        b.mkdirs();
        c.mkdirs();
        File f1 = new File(a.getAbsolutePath() + File.separator + "F1");
        File f2 = new File(a.getAbsolutePath() + File.separator + "F2");
        f1.createNewFile();
        f2.createNewFile();

        File f3 = new File(b.getAbsolutePath() + File.separator + "F3.txt");
        File f4 = new File(b.getAbsolutePath() + File.separator + "F4.txt");
        f3.createNewFile();
        f4.createNewFile();

        File f5 = new File(c.getAbsolutePath() + File.separator + "F5.md");
        File f6 = new File(c.getAbsolutePath() + File.separator + "F6.md");
        f5.createNewFile();
        f6.createNewFile();

        return file;
    }

    @Test
    public void test1() throws IOException {
        URL[] urls = directories.getURLs();
        assertEquals(urls.length, 6);

        urls = directories.getURLs(new String[] {"txt"});
        assertEquals(urls.length, 2);

        urls = directories.getURLs(new String[] {"md"});
        assertEquals(urls.length, 2);

        Collection<File> files = directories.getFiles();
        assertEquals(files.size(), 6);

        assert directories.toString() != null;
    }

    @Test
    public void test2() {
        String tree = new EzyDirectories()
                .directory("tree-for-test/a.txt")
                .printTree(true);
        System.out.println(tree);
    }

    @Test
    public void test3() {
        Collection<File> files = new EzyDirectories()
                .directory("tree-for-test")
                .getFiles(new String[] {"txt"});
        System.out.println(files);

        Collection<File> files1 = new EzyDirectories()
                .directory("tree-for-test")
                .getFiles(new String[] {"txt"}, false);
        System.out.println(files1);
    }

    @Test
    public void deleteFolderTest() throws IOException {
        // given
        EzyFileUtil.createFileIfNotExists(new File("deleteFolderTest/hello/world.txt"));
        EzyFileUtil.createFileIfNotExists(new File("deleteFolderTest/foo/bar.txt"));

        // when
        // then
        EzyDirectories.deleteFolder(new File("deleteFolderTest"));
    }

    @Test
    public void copyFolderTest() throws IOException {
        // given
        EzyFileUtil.createFileIfNotExists(new File("copyFolderTest/hello/world.txt"));
        EzyFileUtil.createFileIfNotExists(new File("copyFolderTest/foo/bar.txt"));
        new File("copyFolderTest/no-files/").mkdir();
        
        // when
        EzyDirectories.copyFolder(new File("not found"), new File("not found/copy"));
        EzyDirectories.copyFolder(new File("copyFolderTest"), new File("copyFolderTest/copy"));
        
        // then
        Asserts.assertTrue(new File("copyFolderTest/copy/hello/world.txt").exists());
        Asserts.assertTrue(new File("copyFolderTest/copy/foo/bar.txt").exists());
        EzyDirectories.deleteFolder(new File("copyFolderTest"));
    }}