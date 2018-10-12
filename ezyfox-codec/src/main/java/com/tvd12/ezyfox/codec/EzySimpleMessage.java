package com.tvd12.ezyfox.codec;

import java.util.Arrays;

import lombok.Getter;

@Getter
public class EzySimpleMessage implements EzyMessage {

	private int size;
	private byte[] content;
	private EzyMessageHeader header;
	private int byteCount;
	
	public EzySimpleMessage(EzyMessageHeader header, byte[] content, int size) {
		this.header = header;
		this.content = content;
		this.size = size;
		this.byteCount = 1 + getSizeLength() + getContent().length;
	}
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("(")
				.append("header: ")
					.append(header)
					.append(", ")
				.append("size: ")
					.append(size)
					.append(", ")
				.append("byteCount: ")
					.append(byteCount)
					.append(", ")
				.append("content: ")
					.append(Arrays.toString(content))
				.append(")")
				.toString();
	}
	
}
