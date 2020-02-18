package com.tvd12.ezyfox.stream;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class EzyInputStreams {

	public static final int EOF = -1;
	public static final int DEFAULT_BUFFER_SIZE = 1024;
	
	private EzyInputStreams() {
	}
	
	public static byte[] toByteArray(InputStream stream) 
			throws IOException {
		byte[] bytes = toByteArray(stream, DEFAULT_BUFFER_SIZE);
		return bytes;
	}
	
	public static byte[] toByteArray(InputStream stream, int bufferSize) 
			throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[bufferSize];
		int nRead;
		while ((nRead = stream.read(buffer)) != EOF)
			outStream.write(buffer, 0, nRead);
		byte[] bytes = outStream.toByteArray();
		return bytes;
	}
	
	public static String toStringUtf8(InputStream stream) throws IOException {
		return toString(stream, StandardCharsets.UTF_8);
	}
	
	public static String toString(InputStream stream, Charset charset)
			throws IOException {
		byte[] bytes = toByteArray(stream);
		return new String(bytes, charset);
	}
	
	public static List<String> toLines(InputStream stream)
			throws IOException {
		List<String> lines = toLines(stream, Charset.defaultCharset());
		return lines;
	}
	
	public static List<String> toLines(InputStream stream, String charset)
			throws IOException {
		List<String> lines = toLines(stream, Charset.forName(charset));
		return lines;
	}
	
	public static List<String> toLines(InputStream stream, Charset charset)
			throws IOException {
		InputStreamReader streamReader = new InputStreamReader(stream, charset);
		BufferedReader bufferedReader = new BufferedReader(streamReader);
        List<String> lines = new ArrayList<>();
        String line = null;
        while ((line = bufferedReader.readLine()) != null) 
        	lines.add(line);
        return lines;
	}
}
