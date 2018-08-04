package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.codec.EzyMessageHeader;

public interface EzyMessage {

	/**
	 * Get size of message
	 * 
	 * @return the size of message
	 */
	int getSize();
	
	/**
	 * Get content of message in byte array
	 * 
	 * @return the content of message
	 */
	byte[] getContent();
	
	/**
	 * Get header of message
	 * 
	 * @return the message header
	 */
	EzyMessageHeader getHeader();
	
	/**
	 * Get count of bytes
	 * 
	 * @return the count of bytes
	 */
	int getByteCount();
	
	/**
	 * Get length of message's size, 2 or 4
	 * 
	 * @return the length of message's size
	 */
	default int getSizeLength() {
		return hasBigSize() ? 4 : 2;
	}
	
	default boolean hasBigSize() {
		return getHeader().isBigSize();
	}
	
}
