package com.tvd12.ezyfox.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EzyWrap<T> {

	protected T value;
	
	public EzyWrap() {
		this(null);
	}
	
	public EzyWrap(T value) {
		this.value = value;
	}
	
	public boolean hasValue() {
		return value != null;
	}
	
	public boolean hasNoValue() {
		return value == null;
	}
	
}
