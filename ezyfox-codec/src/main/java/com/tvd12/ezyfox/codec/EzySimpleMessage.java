package com.tvd12.ezyfox.codec;

import java.util.Arrays;

import lombok.Getter;

@Getter
public class EzySimpleMessage implements EzyMessage {

	private final int size;
	private final byte[] content;
	private final EzyMessageHeader header;
	private final int byteCount;
	
	public EzySimpleMessage(EzyMessageHeader header, byte[] content, int size) {
		this.header = header;
		this.content = content;
		this.size = size;
		this.byteCount = 1 + getSizeLength() + content.length;
	}
	
	@Override
	public boolean hasBigSize() {
		return header.isBigSize();
	}
	
	@Override
	public int getContentStartIndex() {
		if(header.isRawBytes())
			return 0;
		int index = 1 + getSizeLength();
		return index;
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
