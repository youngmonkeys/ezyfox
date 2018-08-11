package com.tvd12.ezyfox.binding.testing.exception2;

import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.impl.EzySimpleBindingContext;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.util.EzyEntityBuilders;

public class Exception2Binding2 extends EzyEntityBuilders {

	public static void main(String[] args) throws Exception {
		EzySimpleBindingContext context = EzySimpleBindingContext.builder()
				.addClass(Exception2ClassB.class)
				.build();
		EzyUnmarshaller unmarshaller = context.newUnmarshaller();
		Exception2ClassB answer = unmarshaller.unmarshal(
				EzyEntityFactory.create(EzyObjectBuilder.class)
				.append("value", "abc")
				.build(), 
				Exception2ClassB.class);
		System.out.println(answer);
	}
}
