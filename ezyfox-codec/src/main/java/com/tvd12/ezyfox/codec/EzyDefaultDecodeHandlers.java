package com.tvd12.ezyfox.codec;

import static com.tvd12.ezyfox.codec.EzyDecodeState.PREPARE_MESSAGE;
import static com.tvd12.ezyfox.codec.EzyDecodeState.READ_MESSAGE_CONTENT;
import static com.tvd12.ezyfox.codec.EzyDecodeState.READ_MESSAGE_HEADER;
import static com.tvd12.ezyfox.codec.EzyDecodeState.READ_MESSAGE_SIZE;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Queue;

import lombok.Setter;

public class EzyDefaultDecodeHandlers extends EzyDecodeHandlers {
	
	protected EzyDefaultDecodeHandlers(Builder builder) {
		super(builder);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder extends EzyDecodeHandlers.Builder {
		protected int maxSize;
		protected EzyByteBufferMessageReader messageReader = new EzyByteBufferMessageReader();
		
		public Builder maxSize(int maxSize) {
			this.maxSize = maxSize;
			return this;
		}
		
		public EzyDefaultDecodeHandlers build() {
			return new EzyDefaultDecodeHandlers(this);
		}
		
		@Override
		protected void addHandlers(
				Map<EzyIDecodeState, EzyDecodeHandler> answer) {
			EzyDecodeHandler readMessgeHeader = new ReadMessageHeader();
			EzyDecodeHandler prepareMessage = new PrepareMessage();
			EzyDecodeHandler readMessageSize = new ReadMessageSize(maxSize);
			EzyDecodeHandler readMessageContent = new ReadMessageContent();
			answer.put(PREPARE_MESSAGE, newHandler(prepareMessage, readMessgeHeader));
			answer.put(READ_MESSAGE_HEADER, newHandler(readMessgeHeader, readMessageSize));
			answer.put(READ_MESSAGE_SIZE, newHandler(readMessageSize, readMessageContent));
			answer.put(READ_MESSAGE_CONTENT, newHandler(readMessageContent));
		}
		
		
		private EzyDecodeHandler newHandler(EzyDecodeHandler handler) {
			return newHandler(handler, null);
		}
		
		private EzyDecodeHandler newHandler(EzyDecodeHandler handler, EzyDecodeHandler next) {
			return newHandler((AbstractDecodeHandler)handler, next);
		}
		
		private EzyDecodeHandler newHandler(AbstractDecodeHandler handler, EzyDecodeHandler next) {
			handler.setNextHandler(next);
			handler.setMessageReader(messageReader);
			return handler;
		}
	}
}

@Setter
abstract class AbstractDecodeHandler implements EzyDecodeHandler {

	protected EzyDecodeHandler nextHandler;
	protected EzyByteBufferMessageReader messageReader;
	
	@Override
	public EzyDecodeHandler nextHandler() {
		return nextHandler;
	}
}

class PrepareMessage extends AbstractDecodeHandler {
	
	@Override
	public EzyIDecodeState nextState() {
		return READ_MESSAGE_HEADER;
	}
	
	@Override
	public boolean handle(ByteBuffer in, Queue<EzyMessage> out) {
		messageReader.clear();
		return true;
	}
}

class ReadMessageHeader extends AbstractDecodeHandler {

	@Override
	public EzyIDecodeState nextState() {
		return READ_MESSAGE_SIZE;
	}

	@Override
	public boolean handle(ByteBuffer in, Queue<EzyMessage> out) {
		return messageReader.readHeader(in);
	}
	
}

class ReadMessageSize extends AbstractDecodeHandler {

	protected final int maxSize;
	
	public ReadMessageSize(int maxSize) {
		this.maxSize = maxSize;
	}
	
	@Override
	public EzyIDecodeState nextState() {
		return READ_MESSAGE_CONTENT;
	}

	@Override
	public boolean handle(ByteBuffer in, Queue<EzyMessage> out) {
		return messageReader.readSize(in, maxSize);
	}
}

class ReadMessageContent extends AbstractDecodeHandler {
	
	@Override
	public EzyIDecodeState nextState() {
		return PREPARE_MESSAGE;
	}

	@Override
	public boolean handle(ByteBuffer in, Queue<EzyMessage> out) {
		if(!messageReader.readContent(in))
			return false;
		out.add(messageReader.get());
		return true;
	}
	
}