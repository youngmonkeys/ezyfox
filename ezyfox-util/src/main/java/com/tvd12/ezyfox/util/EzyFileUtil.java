package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.collect.Sets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import static com.tvd12.ezyfox.util.EzyReturner.returnNotNull;

public final class EzyFileUtil {

    public static final char EXTENSION_SEPARATOR = '.';
    private static final int NOT_FOUND = -1;
    private static final String EMPTY_STRING = "";
    private static final Predicate<File> EMPTY_FILTER = t -> true;

    private EzyFileUtil() {}

    public static Collection<File> listFiles(File directory, boolean recursive) {
        return listFiles(directory, EMPTY_FILTER, recursive);
    }

    public static Collection<File> listFiles(
        File directory,
        String[] extensions,
        boolean recursive
    ) {
        return listFiles(directory, Sets.newHashSet(extensions), recursive);
    }

    public static Collection<File> listFiles(
        File directory,
        Set<String> extensions,
        boolean recursive
    ) {
        ExtensionsFilter filter = new ExtensionsFilter(extensions);
        return listFiles(directory, filter, recursive);
    }

    public static Collection<File> listFiles(
        File directory,
        Predicate<File> filter,
        boolean recursive
    ) {
        List<File> files = new ArrayList<>();
        listFiles0(directory, filter, recursive, files);
        return files;
    }

    private static void listFiles0(
        File directory,
        Predicate<File> filter,
        boolean recursive,
        Collection<File> output
    ) {
        File[] files = returnNotNull(directory.listFiles(), new File[0]);
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }
            if (filter.test(file)) {
                output.add(file);
            }
        }
        if (recursive) {
            for (File file : files) {
                if (file.isDirectory()) {
                    listFiles0(file, filter, true, output);
                }
            }
        }
    }

    public static String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf(EXTENSION_SEPARATOR);
        if (index == NOT_FOUND) {
            return EMPTY_STRING;
        }
        return fileName.substring(index + 1);
    }

    public static boolean createFileIfNotExists(File file) throws IOException {
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.exists()) {
                parentFile.mkdirs();
            }
            return file.createNewFile();
        }
        return false;
    }

    public static String getFileName(String filePath) {
        int index = filePath.lastIndexOf('/');
        if (index < 0) {
            return filePath;
        }
        if (index >= filePath.length() - 1) {
            return filePath;
        }
        return filePath.substring(index + 1);
    }

    public static String getFileNameWithoutExtension(String filePath) {
        String fileName = getFileName(filePath);
        int index = fileName.lastIndexOf('.');
        if (index == 0) {
            return EMPTY_STRING;
        }
        if (index > 0) {
            return fileName.substring(0, index);
        }
        return fileName;
    }

    public static void copyFile(File from, File to) throws IOException {
        EzyFileUtil.createFileIfNotExists(to);
        Files.copy(from.toPath(), to.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    private static class ExtensionsFilter implements Predicate<File> {
        private final Set<String> extensions;

        public ExtensionsFilter(Set<String> extensions) {
            this.extensions = extensions;
        }

        @Override
        public boolean test(File file) {
            String fileName = file.getName();
            String fileExtension = getFileExtension(fileName);
            return extensions.contains(fileExtension);
        }
    }
}
