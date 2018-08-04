package com.tvd12.ezyfox.data;

import java.lang.annotation.Annotation;
import java.util.Set;

import com.google.common.collect.Sets;
import com.tvd12.ezyfox.data.annotation.EzyIndexedData;
import com.tvd12.ezyfox.identifier.EzySimpleIdFetchers;
import com.tvd12.ezyfox.message.annotation.EzyMessage;

public class EzyIndexedDataIdFetchers extends EzySimpleIdFetchers {

	public EzyIndexedDataIdFetchers(Builder builder) {
		super(builder);
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder extends EzySimpleIdFetchers.Builder {
	
		@SuppressWarnings("unchecked")
		@Override
		protected Set<Class<? extends Annotation>> getAnnotationClasses() {
			return Sets.newHashSet(EzyMessage.class, EzyIndexedData.class);
		}
	
	}
	
}
