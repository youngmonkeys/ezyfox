package com.tvd12.ezyfox.function;

import com.tvd12.ezyfox.function.EzyDeserializer;
import com.tvd12.ezyfox.function.EzySerializer;

public interface EzyTemplate<I, O> 
		extends EzySerializer<I, O>, EzyDeserializer<O, I> {

}
