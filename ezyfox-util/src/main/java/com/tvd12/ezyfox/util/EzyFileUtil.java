package com.tvd12.ezyfox.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import com.tvd12.ezyfox.collect.Sets;

public final class EzyFileUtil {

	private static final int NOT_FOUND = -1;
	private static final String EMPTY_STRING = "";
	public static final char EXTENSION_SEPARATOR = '.';
	private static final Predicate<File> EMPTY_FILTER = t -> true;
	
	private EzyFileUtil() {
	}
	
	public static Collection<File> listFiles(File directory, boolean recursive) {
        return listFiles(directory, EMPTY_FILTER, recursive);
    }
	
	public static Collection<File> listFiles(
			File directory, 
			String[] extensions, boolean recursive) {
        return listFiles(directory, Sets.newHashSet(extensions), recursive);
    }
	
	public static Collection<File> listFiles(
			File directory, 
			Set<String> extensions, boolean recursive) {
        ExtensionsFilter filter = new ExtensionsFilter(extensions);
        return listFiles(directory, filter, recursive);
    }
	
	public static Collection<File> listFiles(
			File directory, 
			Predicate<File> filter, boolean recursive) {
        List<File> files = new ArrayList<>();
        listFiles0(directory, filter, recursive, files);
        return files;
    }
	
	private static void listFiles0(
			File directory, 
			Predicate<File> filter, 
			boolean recursive, Collection<File> output) {
        File[] files = directory.listFiles();
        for(File file : files) {
        		if(file.isDirectory())
        			continue;
        		if(filter.test(file))
        			output.add(file);
        }
        if(recursive) {
        		for(File file : files) {
        			if(file.isDirectory())
        				listFiles0(file, filter, recursive, output);
        		}
        }
    }
	
	public static String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf(EXTENSION_SEPARATOR);
        if (index == NOT_FOUND)
            return EMPTY_STRING;
        String answer = fileName.substring(index + 1);
        return answer;
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
	    		if(extensions.contains(fileExtension))
	    			return true;
	    		return false;
		}
	}
}
