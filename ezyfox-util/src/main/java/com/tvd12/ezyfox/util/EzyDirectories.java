/**
 * 
 */
package com.tvd12.ezyfox.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * @author tavandung12
 *
 */
public class EzyDirectories {

    private File directory;
    
    public URL[] getURLs() throws IOException {
        return getURLs(getFiles());
    }
    
    public URL[] getURLs(String[] extensions) throws IOException {
            return getURLs(extensions, true);
    }
    
    public URL[] getURLs(String[] extensions, boolean recursive) throws IOException {
            return getURLs(getFiles(extensions, recursive));
    }
    
    public Collection<File> getFiles() {
            return EzyFileUtil.listFiles(directory, true);
        }
    
    public Collection<File> getFiles(String[] extensions) {
            return getFiles(extensions, true);
    }
    
    public Collection<File> getFiles(String[] extensions, boolean recursive) {
        return EzyFileUtil.listFiles(directory, extensions, recursive);
    }
    
    public String printTree(boolean printFile) {
            return EzyFolderTreePrinter.builder()
                .printFile(printFile)
                .build()
                .print(directory);
    }
    
    private URL[] getURLs(Collection<File> files) throws IOException {
            int index = 0;
            URL[] urls = new URL[files.size()];
        for (File file : files)
            urls[index ++] = file.toURI().toURL();
        return urls;
    }
    
    public EzyDirectories directory(File directory) {
        this.directory = directory;
        return this;
    }
    
    public EzyDirectories directory(String directoryPath) {
        return directory(new File(directoryPath));
    }
    
    @Override
    public String toString() {
        return directory.toString();
    }
    
    public static void deleteFolder(File folder) throws IOException {
        deleteFolder(folder, it -> {});
    }
    
    public static void deleteFolder(
        File folder, 
        Consumer<File> callback
    ) throws IOException {
        Stack<File> stack = new Stack<>();
        stack.push(folder);
        while (stack.size() > 0) {
            File parent = stack.pop();
            File[] fileList = parent.listFiles();
            if (EzyArrayUtil.isEmpty(fileList)) {
                Files.deleteIfExists(parent.toPath());
                callback.accept(parent);
                continue;
            }
            stack.push(parent);
            for (File child : fileList) {
                if (child.isDirectory()) {
                    stack.push(child);
                } else {
                    Files.delete(child.toPath());
                    callback.accept(child);
                }
            }
        }
    }
    
    public static void copyFolder(File from, File to) throws IOException {
        copyFolder(from, to, it -> {});
    }
    
    public static void copyFolder(
            File from, 
            File to, 
            Consumer<File> callback) throws IOException {
        if (!from.exists()) {
            return;
        }
        Stack<File> stack = new Stack<>();
        stack.push(from);
        while (stack.size() > 0) {
            File parent = stack.pop();
            File[] fileList = parent.listFiles();
            if (EzyArrayUtil.isEmpty(fileList)) {
                continue;
            }
            for (File child : fileList) {
                if (child.isDirectory()) {
                    stack.push(child);
                } else {
                    File toFile = Paths.get(
                        to.toString(), 
                        subpath(child, from).toString()
                    ).toFile();
                    EzyFileUtil.copyFile(child, toFile);
                    callback.accept(child);
                }
            }
        }
    }
    
    public static File subpath(File fullPath, File rootPath) {
        return subpath(fullPath.toPath(), rootPath.toPath()).toFile();
    }
    
    public static Path subpath(Path fullPath, Path rootPath) {
        return fullPath.subpath(rootPath.getNameCount(), fullPath.getNameCount());
    }}