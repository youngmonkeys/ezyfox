package com.tvd12.ezyfox.codec;

public class EzySimpleStringDataEncoder implements EzyStringDataEncoder {

    private EzyObjectToStringEncoder encoder;

    public EzySimpleStringDataEncoder(EzyObjectToStringEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public byte[] encode(Object data) throws Exception {
        byte[] answer = encoder.encode(data);
        return answer;
    }

    @Override
    public <T> T encode(Object data, Class<T> outType) throws Exception {
        T answer = encoder.encode(data, outType);
        return answer;
    }
}