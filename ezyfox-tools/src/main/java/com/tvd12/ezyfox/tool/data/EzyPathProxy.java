package com.tvd12.ezyfox.tool.data;

import java.io.File;
import java.nio.file.Path;

import com.tvd12.ezyfox.util.EzyFileUtil;

import lombok.Getter;

public class EzyPathProxy {

	@Getter
	protected final Path path;
	
	public EzyPathProxy(Path path) {
		this.path = path;
	}
	
	public File toFile() {
		File file = path.toFile();
		return file;
	}
	
	public String getFileName() {
		String name = path.getFileName().toString();
		return name;
	}
	
	public String getFileExtension() {
		String extension = EzyFileUtil.getFileExtension(getFileName());
		return extension;
	}
	
	@Override
	public String toString() {
		return path.toString();
	}
	
}
