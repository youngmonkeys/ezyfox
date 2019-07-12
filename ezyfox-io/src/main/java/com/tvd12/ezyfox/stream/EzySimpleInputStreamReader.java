package com.tvd12.ezyfox.stream;

import static com.tvd12.ezyfox.util.EzyReturner.returnWithIllegalArgumentException;

import java.io.InputStream;
import java.util.List;

import com.tvd12.ezyfox.builder.EzyBuilder;

public class EzySimpleInputStreamReader implements EzyInputStreamReader {

	protected EzySimpleInputStreamReader(Builder builder) {
	}
	
	@Override
	public byte[] readBytes(InputStream stream) {
		try {
			byte[] bytes = EzyInputStreams.toByteArray(stream);
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
	public List<String> readLines(InputStream stream, String charset) {
		try {
			List<String> lines = EzyInputStreams.toLines(stream, charset);
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
