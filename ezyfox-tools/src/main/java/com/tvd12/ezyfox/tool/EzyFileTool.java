package com.tvd12.ezyfox.tool;

import java.nio.file.Files;
import java.nio.file.Path;

public class EzyFileTool {

	public static void createParentDir(Path path) {
		try {
			Path parent = path.getParent();
			if(parent != null)
				Files.createDirectories(parent);
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
}
