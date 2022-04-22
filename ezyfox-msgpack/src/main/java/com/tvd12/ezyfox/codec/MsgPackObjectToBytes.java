package com.tvd12.ezyfox.codec;

public class MsgPackObjectToBytes implements EzyObjectToBytes {

    protected final EzyMessageSerializer serializer;

    public MsgPackObjectToBytes(EzyMessageSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public byte[] convert(Object object) {
        try {
            return serializer.serialize(object);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}
