package com.tvd12.ezyfox.codec;

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
	
	@Override
	public byte[] toMessageContent(Object data) throws Exception {
		return objectToMessage.convertToMessageContent(data);
	}
	
	@Override
	public byte[] encryptMessageContent(
			byte[] messageContent, byte[] encryptionKey) throws Exception {
		EzyMessage message = objectToMessage.packToMessage(
				doEncrypt(messageContent, encryptionKey), 
				true);
		return convertMessageToBytes(message);
	}
	
	protected byte[] doEncrypt(
			byte[] messageContent, byte[] encryptionKey) throws Exception {
		return messageContent;
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
