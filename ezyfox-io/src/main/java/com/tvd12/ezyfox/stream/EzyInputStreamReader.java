package com.tvd12.ezyfox.stream;

import java.io.InputStream;
import java.util.List;

public interface EzyInputStreamReader {

	byte[] readBytes(InputStream stream);
	
	char[] readChars(InputStream stream, String charset);
	
	String readString(InputStream stream, String charset);
	
	List<String> readLines(InputStream stream, String charset);
	
}
