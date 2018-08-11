package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.codec.EzyMessageHeader;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EzySimpleMessageHeader implements EzyMessageHeader {

	protected boolean bigSize;
	protected boolean encrypted;
	protected boolean compressed;
	protected boolean text;
	
	@Override
	public String toString() {
		return new StringBuilder()
				.append("<")
				.append("bigSize: ")
					.append(bigSize)
					.append(", ")
				.append("encrypted: ")
					.append(encrypted)
					.append(", ")
				.append("compressed: ")
					.append(compressed)
					.append(", ")
				.append("text: ")
					.append(text)
				.append(">")
				.toString();
	}
	
}
