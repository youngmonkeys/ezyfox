package com.tvd12.ezyfox.binding.testing.exception1;

import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.impl.EzySimpleBindingContext;
import com.tvd12.ezyfox.builder.EzyObjectBuilder;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.util.EzyEntityBuilders;

public class Exception1Binding2 extends EzyEntityBuilders {

    public static void main(String[] args) {
        EzySimpleBindingContext context = EzySimpleBindingContext.builder()
            .addClass(Exception1ClassB.class)
            .build();
        EzyUnmarshaller unmarshaller = context.newUnmarshaller();
        unmarshaller.unmarshal(
            EzyEntityFactory.create(EzyObjectBuilder.class)
                .append("value", "abc")
                .build(),
            Exception1ClassB.class);
    }
}
