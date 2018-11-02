package com.tvd12.ezyfox.codec;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.util.EzyResettable;

public abstract class EzyDecodeHandlers implements EzyResettable {

	protected EzyIDecodeState state;
	protected Map<EzyIDecodeState, EzyDecodeHandler> handlers;
	
	protected EzyDecodeHandlers(Builder builder) {
		this.state = firstState();
		this.handlers = builder.newHandlers();
	}
	
	public void handle(ByteBuffer in, Queue<EzyMessage> out) {
		EzyDecodeHandler handler = handlers.get(state);
		while(handler != null && handler.handle(in, out)) {
			state = handler.nextState();
			handler = handler.nextHandler();
		}
	}
	
	protected EzyIDecodeState firstState() {
		return EzyDecodeState.PREPARE_MESSAGE;
	}
	
	@Override
	public void reset() {
		this.state = firstState();
	}
	
	public abstract static class Builder implements EzyBuilder<EzyDecodeHandlers> {
		
		protected Map<EzyIDecodeState, EzyDecodeHandler> newHandlers() {
			Map<EzyIDecodeState, EzyDecodeHandler> answer = new HashMap<>();
			addHandlers(answer);
			return answer;
		}
		
		protected abstract void addHandlers(Map<EzyIDecodeState, EzyDecodeHandler> answer);
	}
	
}
