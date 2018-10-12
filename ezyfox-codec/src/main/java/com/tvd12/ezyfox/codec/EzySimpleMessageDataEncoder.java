package com.tvd12.ezyfox.codec;

public class EzySimpleMessageDataEncoder implements EzyMessageDataEncoder {

	private EzyObjectToByteEncoder encoder;
	
	public EzySimpleMessageDataEncoder(EzyObjectToByteEncoder encoder) {
		this.encoder = encoder;
	}
	
	@Override
	public byte[] encode(Object data) throws Exception {
		return encoder.encode(data);
	}

}
