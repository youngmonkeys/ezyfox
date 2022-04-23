package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.callback.EzyCallback;
import com.tvd12.ezyfox.util.EzyLoggable;

public class EzySimpleStringDataDecoder
        extends EzyLoggable
        implements EzyStringDataDecoder {

    protected final EzyStringToObjectDecoder decoder;

    public EzySimpleStringDataDecoder(EzyStringToObjectDecoder decoder) {
        this.decoder = decoder;
    }

    @Override
    public void decode(String bytes, EzyCallback<Object> callback) throws Exception {
        Object answer = decoder.decode(bytes);
        callback.call(answer, bytes.length());
    }

    @Override
    public void decode(byte[] bytes, EzyCallback<Object> callback) throws Exception {
        Object answer = decoder.decode(bytes);
        callback.call(answer, bytes.length);
    }

    @Override
    public void destroy() {
    }
}
