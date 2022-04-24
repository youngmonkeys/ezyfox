package com.tvd12.ezyfox.message;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.identifier.EzyIdFetchers;
import com.tvd12.ezyfox.identifier.EzySimpleIdFetchers;
import com.tvd12.ezyfox.message.annotation.EzyMessage;
import com.tvd12.ezyfox.message.annotation.Message;

import java.lang.annotation.Annotation;
import java.util.Set;

public class EzyMessageIdFetchers extends EzySimpleIdFetchers {

    public EzyMessageIdFetchers(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EzySimpleIdFetchers.Builder {

        @SuppressWarnings("unchecked")
        @Override
        protected Set<Class<? extends Annotation>> getAnnotationClasses() {
            return Sets.newHashSet(EzyMessage.class, Message.class);
        }

        @Override
        protected EzyIdFetchers newProduct() {
            return new EzyMessageIdFetchers(this);
        }
    }
}
