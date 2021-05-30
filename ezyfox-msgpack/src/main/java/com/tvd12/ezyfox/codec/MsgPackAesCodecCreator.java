package com.tvd12.ezyfox.codec;

public class MsgPackAesCodecCreator implements EzyCodecCreator {

	protected final EzyMessageToBytes messageToBytes;
	protected final EzyObjectToMessage objectToMessage; 
	protected final EzyMessageDeserializer deserializer; 
	
	public MsgPackAesCodecCreator() {
		this.messageToBytes = new EzySimpleMessageToBytes();
		this.objectToMessage = new MsgPackObjectToMessage();
		this.deserializer = new MsgPackSimpleDeserializer();
	}
	
	@Override
	public EzyByteToObjectDecoder newDecoder(int maxRequestSize) {
		return new MsgPackAesByteToObjectDecoder(deserializer, maxRequestSize);
	}
	
	@Override
	public EzyObjectToByteEncoder newEncoder() {
		return new MsgPackAesObjectToByteEncoder(messageToBytes, objectToMessage);
	}

}