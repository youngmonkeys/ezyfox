package com.tvd12.ezyfox.stream;

import static com.tvd12.ezyfox.util.EzyReturner.returnWithIllegalArgumentException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tvd12.ezyfox.builder.EzyBuilder;

public class EzySimpleInputStreamReader implements EzyInputStreamReader {

	public static final int EOF = -1;
	private static final int DEFAULT_BUFFER_SIZE = 1024;
	
	protected EzySimpleInputStreamReader(Builder builder) {
	}
	
	@Override
	public byte[] readBytes(InputStream stream) {
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int nRead;
			while ((nRead = stream.read(buffer)) != -1)
				outStream.write(buffer, 0, nRead);
			byte[] bytes = outStream.toByteArray();
			return bytes;
		}
		catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public String readString(InputStream stream, String charset) {
		return returnWithIllegalArgumentException(() -> new String(readBytes(stream), charset));
	}
	
	@Override
	public char[] readChars(InputStream stream, String charset) {
		return readString(stream, charset).toCharArray();
	}
	
	@Override
	public Collection<String> readLines(InputStream stream, String charset) {
		try {
			InputStreamReader streamReader = new InputStreamReader(stream, Charset.forName(charset));
			BufferedReader bufferedReader = new BufferedReader(streamReader);
	        List<String> lines = new ArrayList<>();
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) 
	        	lines.add(line);
	        return lines;
		}
		catch(Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder implements EzyBuilder<EzyInputStreamReader> {

		@Override
		public EzyInputStreamReader build() {
			return new EzySimpleInputStreamReader(this);
		}
	}
	
}
