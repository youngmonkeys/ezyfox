package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.exception.EzyMaxRequestSizeException;

public abstract class EzyMessageReader<B> {
	
	protected int size;
	protected byte[] content;
	protected byte headerByte;
	protected byte[] sizeBytes;
	protected EzyMessageHeader header;
	protected EzyMessageHeaderReader headerReader = newMessageHeaderReader();
	
	private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
	
	public EzyMessageReader() {
		clear();
	}

	protected abstract int remaining(B buffer);
	protected abstract byte readByte(B buffer);
	protected abstract int readMessgeSize(B buffer);
	protected abstract void readMessageContent(B buffer, byte[] content, int offset, int length);
	
	protected EzyMessageHeaderReader newMessageHeaderReader() {
		return new EzyMessageHeaderReader();
	}
	
	public boolean readHeader(B buffer) {
		int remaining = remaining(buffer);
		if(remaining < getHeaderLength())
			return false;
		headerByte = readByte(buffer);
		readHeader(headerByte);
		return true;
	}
	
	public boolean readSize(B buffer, int maxSize) {
		int remaining = remaining(buffer);
		if(remaining < getSizeLength())
			return false;
		this.size = readMessgeSize(buffer);
		if(size > maxSize)
			throw new EzyMaxRequestSizeException(size, maxSize);
		return true;
	}
	
	public boolean readContent(B buffer) {
		int remaining = remaining(buffer);
		if(remaining < size)
			return false;
		boolean rawBytes = isRawBytes();
		if(rawBytes) {
			int offset = getHeaderLength() + sizeBytes.length;
			int rawSize = size + offset;
			this.content = new byte[rawSize];
			this.content[0] = headerByte;
			System.arraycopy(sizeBytes, 0, sizeBytes.length, 1, sizeBytes.length);
			readMessageContent(buffer, content, offset, size);
		}
		else {
			this.content = new byte[size];
			readMessageContent(buffer, content, 0, size);
		}
		return true;
	}
	
	public void clear() {
		this.size = 0;
		this.headerByte = 0;
		this.sizeBytes = EMPTY_BYTE_ARRAY;
		this.content = EMPTY_BYTE_ARRAY;
	}
	
	public EzyMessage get() {
		return new EzySimpleMessage(header, content, size);
	}
	
	private void readHeader(byte header) {
		this.header = headerReader.read(header);
	}
	
	protected int getSizeLength() {
		return header.isBigSize() ? 4 : 2;
	}
	
	protected boolean isRawBytes() {
		return header.isRawBytes();
	}
	
	protected int getHeaderLength() {
		return 1;
	}
}
