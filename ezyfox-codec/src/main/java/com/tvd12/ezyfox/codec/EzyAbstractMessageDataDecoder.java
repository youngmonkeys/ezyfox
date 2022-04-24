package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.util.EzyDestroyable;
import com.tvd12.ezyfox.util.EzyLoggable;

import java.nio.ByteBuffer;

public class EzyAbstractMessageDataDecoder<D>
    extends EzyLoggable
    implements EzyDestroyable {

    protected final D decoder;
    protected ByteBuffer buffer;
    protected volatile boolean active;

    public EzyAbstractMessageDataDecoder(D decoder) {
        this.active = true;
        this.decoder = decoder;
    }

    @Override
    public void destroy() {
        this.active = false;
    }
}
