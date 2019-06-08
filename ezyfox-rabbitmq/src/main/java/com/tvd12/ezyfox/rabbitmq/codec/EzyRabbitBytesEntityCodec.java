package com.tvd12.ezyfox.rabbitmq.codec;

import com.tvd12.ezyfox.binding.codec.EzyBindingEntityCodec;
import com.tvd12.ezyfox.codec.EzyEntityCodec;

public class EzyRabbitBytesEntityCodec extends EzyBindingEntityCodec {
	
	protected EzyRabbitBytesEntityCodec(Builder builder) {
		super(builder);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder extends EzyBindingEntityCodec.Builder {

		@Override
		public EzyEntityCodec build() {
			return new EzyRabbitBytesEntityCodec(this);
		}
		
	}

}
