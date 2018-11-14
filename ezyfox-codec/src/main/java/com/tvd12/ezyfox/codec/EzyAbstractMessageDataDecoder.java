package com.tvd12.ezyfox.codec;

import java.nio.ByteBuffer;

import com.tvd12.ezyfox.util.EzyLoggable;

public class EzyAbstractMessageDataDecoder<D> extends EzyLoggable {

	protected ByteBuffer buffer;
	protected volatile boolean active;

	protected final D decoder;
	
	public EzyAbstractMessageDataDecoder(D decoder) {
		this.active = true;
		this.decoder = decoder;
	}
	
	public void destroy() {
		this.active = false;
	}
	
}
