package com.tvd12.ezyfox.file;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import com.tvd12.ezyfox.builder.EzyBuilder;

public class EzySimpleFileReader implements EzyFileReader {
	
	public EzySimpleFileReader() {
		this(builder());
	}
	
	protected EzySimpleFileReader(Builder builder) {}

	@Override
	public byte[] readBytes(File file) {
		try {
			Path path = file.toPath();
			byte[] bytes = Files.readAllBytes(path);
			return bytes;
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public Collection<String> readLines(File file, String charset) {
		try {
			Path path = file.toPath();
			Charset cs = Charset.forName(charset);
			Collection<String> lines = Files.readAllLines(path, cs);
			return lines;
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder implements EzyBuilder<EzyFileReader> {

		@Override
		public EzyFileReader build() {
			return new EzySimpleFileReader(this);
		}
	}
	
}
