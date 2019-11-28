package com.tvd12.ezyfox.data;

import java.lang.annotation.Annotation;
import java.util.Set;

import com.tvd12.ezyfox.collect.Sets;
import com.tvd12.ezyfox.data.annotation.EzyIndexedData;
import com.tvd12.ezyfox.data.annotation.IndexedData;
import com.tvd12.ezyfox.identifier.EzyIdFetchers;
import com.tvd12.ezyfox.identifier.EzySimpleIdFetchers;
import com.tvd12.ezyfox.message.annotation.EzyMessage;
import com.tvd12.ezyfox.message.annotation.Message;

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
			return Sets.newHashSet(
					Message.class, IndexedData.class,
					EzyMessage.class, EzyIndexedData.class
			);
		}
		
		@Override
		protected EzyIdFetchers newProduct() {
			return new EzyIndexedDataIdFetchers(this);
		}
	
	}
	
}
