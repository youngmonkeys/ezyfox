package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.codec.EzyMessage;
import com.tvd12.ezyfox.codec.EzyMessageToBytes;
import com.tvd12.ezyfox.codec.EzyObjectToByteEncoder;
import com.tvd12.ezyfox.codec.EzyObjectToMessage;

public class MsgPackObjectToByteEncoder implements EzyObjectToByteEncoder {

	protected final EzyMessageToBytes messageToBytes;
	protected final EzyObjectToMessage objectToMessage;
	
	public MsgPackObjectToByteEncoder(
			EzyMessageToBytes messageToBytes,
			EzyObjectToMessage objectToMessage) {
		this.messageToBytes = messageToBytes;
		this.objectToMessage = objectToMessage;
	}
	
	@Override
	public byte[] encode(Object msg) throws Exception {
		byte[] bytes = convertObjectToBytes(msg);
		return bytes;
	}
	
	protected byte[] convertObjectToBytes(Object object) {
		byte[] bytes = convertMessageToBytes(convertObjectToMessage(object));
		return bytes;
	}
	
	protected EzyMessage convertObjectToMessage(Object object) {
		EzyMessage message = objectToMessage.convert(object);
		return message;
	}
	
	protected byte[] convertMessageToBytes(EzyMessage message) {
		byte[] bytes = messageToBytes.convert(message);
		return bytes;
	}
	
}
