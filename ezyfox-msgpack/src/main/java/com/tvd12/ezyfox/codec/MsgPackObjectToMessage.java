package com.tvd12.ezyfox.codec;

public class MsgPackObjectToMessage implements EzyObjectToMessage {

    protected final EzyObjectToBytes objectToBytes;

    public MsgPackObjectToMessage() {
        this.objectToBytes = new MsgPackObjectToBytes(newSerializer());
    }

    protected EzyMessageSerializer newSerializer() {
        return new MsgPackSimpleSerializer();
    }

    @Override
    public EzyMessage convert(Object object) {
        return packToMessage(convertToMessageContent(object), false);
    }

    @Override
    public byte[] convertToMessageContent(Object object) {
        return objectToBytes.convert(object);
    }

    @Override
    public EzyMessage packToMessage(byte[] content, boolean encrypted) {
        return new EzySimpleMessage(
            newHeader(content, encrypted),
            content,
            content.length
        );
    }

    private EzyMessageHeader newHeader(byte[] content, boolean encrypted) {
        return new EzySimpleMessageHeader(
            isBigMessage(content),
            encrypted,
            false,
            false,
            false,
            false
        );
    }

    private boolean isBigMessage(byte[] content) {
        return content.length > MsgPackConstant.MAX_SMALL_MESSAGE_SIZE;
    }
}
