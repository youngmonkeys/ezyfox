package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.codec.EzyMessage;
import com.tvd12.ezyfox.codec.EzyMessageHeader;
import com.tvd12.ezyfox.codec.EzyMessageSerializer;
import com.tvd12.ezyfox.codec.EzyObjectToBytes;
import com.tvd12.ezyfox.codec.EzyObjectToMessage;

public class MsgPackObjectToMessage implements EzyObjectToMessage {

	protected final EzyObjectToBytes objectToBytes;
	
	public MsgPackObjectToMessage() {
		this.objectToBytes = new MsgPackObjectToBytes(newSerializer());
	}
	
	protected EzyMessageSerializer newSerializer() {
		EzyMessageSerializer serializer = new MsgPackSimpleSerializer();
		return serializer;
	}
	
	@Override
	public EzyMessage convert(Object object) {
		EzyMessage message = convert(convertObject(object));
		return message;
	}
	
	private byte[] convertObject(Object object) {
		byte[] bytes = objectToBytes.convert(object);
		return bytes;
	}
	
	private EzyMessage convert(byte[] content) {
		EzyMessage message = new EzySimpleMessage(newHeader(content), content, content.length);
		return message;
	}
	
	private EzyMessageHeader newHeader(byte[] content) {
		EzyMessageHeader header = new EzySimpleMessageHeader(isBigMessage(content), false, false, false, false, false);
		return header;
	}
	
	private boolean isBigMessage(byte[] content) {
		boolean answer = content.length > MsgPackConstant.MAX_SMALL_MESSAGE_SIZE;
		return answer;
	}
	
}
