package com.tvd12.ezyfox.builder;

import com.tvd12.ezyfox.builder.EzyHasInOutTransformer;
import com.tvd12.ezyfox.function.EzyCreation;
import com.tvd12.ezyfox.io.EzyInputTransformer;
import com.tvd12.ezyfox.io.EzyOutputTransformer;

public class EzyHasInOutTransformer {

	protected final EzyInputTransformer inputTransformer;
	protected final EzyOutputTransformer outputTransformer;
	
	public EzyHasInOutTransformer(
			EzyInputTransformer inputTransformer, 
			EzyOutputTransformer outputTransformer) {
		this.inputTransformer = inputTransformer;
		this.outputTransformer = outputTransformer;
	}
	
	public static abstract class AbstractCreator<
			P extends EzyHasInOutTransformer,
			C extends AbstractCreator<P, C>
			> 
			implements EzyCreation<P> {
		protected EzyInputTransformer inputTransformer;
		protected EzyOutputTransformer outputTransformer;
		
		public C inputTransformer(EzyInputTransformer inputTransformer) {
			this.inputTransformer = inputTransformer;
			return getThis();
		}
		public C outputTransformer(EzyOutputTransformer outputTransformer) {
			this.outputTransformer = outputTransformer;
			return getThis();
		}
		
		@Override
		public abstract P create();
		
		@SuppressWarnings("unchecked")
		protected C getThis() {
			return (C)this;
		}
	}
	
}
