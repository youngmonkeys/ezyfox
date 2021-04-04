package com.tvd12.ezyfox.codec;

public class MsgPackCodecCreator implements EzyCodecCreator {

	protected final EzyMessageToBytes messageToBytes;
	protected final EzyObjectToMessage objectToMessage; 
	protected final EzyMessageDeserializer deserializer; 
	
	public MsgPackCodecCreator() {
		this.messageToBytes = new EzySimpleMessageToBytes();
		this.objectToMessage = new MsgPackObjectToMessage();
		this.deserializer = new MsgPackSimpleDeserializer();
	}
	
	@Override
	public EzyByteToObjectDecoder newDecoder(int maxRequestSize) {
		return new MsgPackByteToObjectDecoder(deserializer, maxRequestSize);
	}
	
	@Override
	public EzyObjectToByteEncoder newEncoder() {
		return new MsgPackObjectToByteEncoder(messageToBytes, objectToMessage);
	}

}
