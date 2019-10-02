package com.tvd12.ezyfox.testing.codec;

import java.util.Map;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.codec.EzyDecodeHandler;
import com.tvd12.ezyfox.codec.EzyDecodeHandlers;
import com.tvd12.ezyfox.codec.EzyIDecodeState;

public class EzyDecodeHandlersTest {

	@Test
	public void test() {
		EzyDecodeHandlers handlers = ExEzyDecodeHandlers.builder()
				.build();
		handlers.reset();
	}
	
	public static class ExEzyDecodeHandlers extends EzyDecodeHandlers {
		
		public ExEzyDecodeHandlers(Builder builder) {
			super(builder);
		}
		
		public static Builder builder() {
			return new Builder();
		}
		
		public static class Builder extends EzyDecodeHandlers.Builder {
			@Override
			protected void addHandlers(Map<EzyIDecodeState, EzyDecodeHandler> answer) {
			}
			
			@Override
			public EzyDecodeHandlers build() {
				return new ExEzyDecodeHandlers(this);
			}
		}
		
	}
	
}
