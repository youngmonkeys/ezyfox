package com.tvd12.ezyfox.tool.data;

import java.io.File;
import java.nio.file.Path;

import com.tvd12.ezyfox.util.EzyFileUtil;

import lombok.Getter;

public class EzyPathProxy {

	@Getter
	protected final Path path;
	@Getter
	protected final Path root;
	
	public EzyPathProxy(Path path) {
		this(null, path);
	}
	
	public EzyPathProxy(Path root, Path path) {
		this.root = root;
		this.path = path;
	}
	
	
	public File toFile() {
		File file = path.toFile();
		return file;
	}
	
	public Path getRelativePath() {
		Path answer = path.relativize(root);
		return answer;
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
