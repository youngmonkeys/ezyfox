package com.tvd12.ezyfox.codec;

public class MsgPackObjectToByteEncoder implements EzyObjectToByteEncoder {

    protected final EzyMessageToBytes messageToBytes;
    protected final EzyObjectToMessage objectToMessage;

    public MsgPackObjectToByteEncoder(
        EzyMessageToBytes messageToBytes,
        EzyObjectToMessage objectToMessage
    ) {
        this.messageToBytes = messageToBytes;
        this.objectToMessage = objectToMessage;
    }

    @Override
    public byte[] encode(Object msg) {
        return convertObjectToBytes(msg);
    }

    @Override
    public byte[] toMessageContent(Object data) {
        return objectToMessage.convertToMessageContent(data);
    }

    @Override
    public byte[] encryptMessageContent(
        byte[] messageContent,
        byte[] encryptionKey
    ) throws Exception {
        EzyMessage message;
        if (encryptionKey != null) {
            message = objectToMessage.packToMessage(
                doEncrypt(messageContent, encryptionKey),
                true
            );
        } else {
            message = objectToMessage.packToMessage(messageContent, false);
        }
        return convertMessageToBytes(message);
    }

    protected byte[] doEncrypt(
        byte[] messageContent,
        byte[] encryptionKey
    ) throws Exception {
        return messageContent;
    }

    protected byte[] convertObjectToBytes(Object object) {
        return convertMessageToBytes(convertObjectToMessage(object));
    }

    protected EzyMessage convertObjectToMessage(Object object) {
        return objectToMessage.convert(object);
    }

    protected byte[] convertMessageToBytes(EzyMessage message) {
        return messageToBytes.convert(message);
    }
}
