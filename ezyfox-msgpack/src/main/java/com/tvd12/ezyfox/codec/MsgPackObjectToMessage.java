package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.codec.EzyMessage;
import com.tvd12.ezyfox.codec.EzyMessageHeader;
import com.tvd12.ezyfox.codec.EzyMessageSerializer;
import com.tvd12.ezyfox.codec.EzyObjectToBytes;
import com.tvd12.ezyfox.codec.EzyObjectToMessage;

public class MsgPackObjectToMessage implements EzyObjectToMessage {

	private EzyObjectToBytes objectToBytes;
	
	protected MsgPackObjectToMessage(Builder builder) {
		this.objectToBytes = builder.newObjectToBytes();
	}
	
	@Override
	public EzyMessage convert(Object object) {
		return convert(convertObject(object));
	}
	
	private byte[] convertObject(Object object) {
		return objectToBytes.convert(object);
	}
	
	private EzyMessage convert(byte[] content) {
		return new EzySimpleMessage(newHeader(content), content, content.length);
	}
	
	private EzyMessageHeader newHeader(byte[] content) {
		return new EzySimpleMessageHeader(isBigMessage(content), false, false, false);
	}
	
	private boolean isBigMessage(byte[] content) {
		return content.length > MsgPackConstant.MAX_SMALL_MESSAGE_SIZE;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {

		public EzyObjectToMessage build() {
			return new MsgPackObjectToMessage(this);
		}
		
		protected EzyObjectToBytes newObjectToBytes() {
			return MsgPackObjectToBytes.builder().serializer(newSerializer()).build();
		}
		
		protected EzyMessageSerializer newSerializer() {
			return new MsgPackSimpleSerializer();
		}
	}
	
}
