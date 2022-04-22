package com.tvd12.ezyfox.message;

import java.lang.annotation.Annotation;
import java.util.Set;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.identifier.EzySimpleIdSetters;
import com.tvd12.ezyfox.message.annotation.EzyMessage;
import com.tvd12.ezyfox.message.annotation.Message;

public class EzyMessageIdSetters extends EzySimpleIdSetters {

    public EzyMessageIdSetters(Builder builder) {
        super(builder);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends EzySimpleIdSetters.Builder {

        @SuppressWarnings("unchecked")
        @Override
        protected Set<Class<? extends Annotation>> getAnnotationClasses() {
            return Sets.newHashSet(EzyMessage.class, Message.class);
        }

        @Override
        protected EzySimpleIdSetters newProduct() {
            return new EzyMessageIdSetters(this);
        }

    }
}