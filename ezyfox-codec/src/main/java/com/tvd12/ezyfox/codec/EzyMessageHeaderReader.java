package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.codec.EzyMessageHeader;

public class EzyMessageHeaderReader {
	
	protected boolean readBigSize(byte header) {
		return (header & 1 << 0) != 0;
	}
	
	protected boolean readEncrypted(byte header) {
		return (header & (1 << 1)) != 0;
	}
	
	protected boolean readCompressed(byte header) {
		return (header & (1 << 2)) != 0;
	}
	
	protected boolean readText(byte header) {
		return (header & (1 << 3)) != 0;
	}
	
	protected boolean readRawBytes(byte header) {
		return (header & (1 << 4)) != 0;
	}
	
	public EzyMessageHeader read(byte header) {
		return new EzySimpleMessageHeader(
				readBigSize(header),
				readEncrypted(header),
				readCompressed(header),
				readText(header),
				readRawBytes(header));
	}
	
}