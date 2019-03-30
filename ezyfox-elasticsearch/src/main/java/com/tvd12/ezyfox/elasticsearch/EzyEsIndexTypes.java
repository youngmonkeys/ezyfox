package com.tvd12.ezyfox.elasticsearch;

import java.util.HashSet;
import java.util.Set;

import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.util.EzyHashMapSet;
import com.tvd12.ezyfox.util.EzyMapSet;

public class EzyEsIndexTypes {
	
	protected final EzyMapSet<String, String> indexTypes;
	
	protected EzyEsIndexTypes(Builder builder) {
		this.indexTypes = builder.indexTypes;
	}
	
	public String getIndex() {
		return indexTypes.keySet().iterator().next();
	}
	
	public String getType() {
		String index = getIndex();
		Set<String> types = getTypes(index);
		return types.iterator().next();
	}
	
	public EzyEsIndexType getIndexType() {
		String index = getIndex();
		String type = getType();
		return new EzyEsIndexType(index, type);
	}
	
	public Set<String> getIndexes() {
		return new HashSet<>(indexTypes.keySet());
	}
	
	public Set<String> getTypes(String index) {
		return new HashSet<>(indexTypes.getItems(index));
	}
	
	public Set<EzyEsIndexType> getIndexTypes() {
		Set<EzyEsIndexType> set = new HashSet<>();
		for(String index : indexTypes.keySet()) {
			Set<String> types = indexTypes.getItems(index);
			for(String type : types) {
				set.add(new EzyEsIndexType(index, type));
			}
		}
		return set;
	}
	
	public void merge(EzyEsIndexTypes other) {
		this.indexTypes.putAll(other.indexTypes);
	}
	
	@Override
	public String toString() {
		return indexTypes.toString();
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder implements EzyBuilder<EzyEsIndexTypes> {

		protected EzyMapSet<String, String> indexTypes = new EzyHashMapSet<>();
		
		public Builder add(EzyEsIndexType indexType) {
			this.indexTypes.addItems(indexType.getIndex(), indexType.getType());
			return this;
		}
		
		public Builder add(EzyEsIndexTypes indexTypes) {
			return add(indexTypes.getIndexTypes());
		}
		
		public Builder add(String index, String type) {
			return add(new EzyEsIndexType(index, type));
		}
		
		public Builder add(Iterable<EzyEsIndexType> indexTypes) {
			indexTypes.forEach(this::add);
			return this;
		}
		
		@Override
		public EzyEsIndexTypes build() {
			return new EzyEsIndexTypes(this);
		}
		
	}

}
