package com.tvd12.ezyfox.util;

import java.util.Arrays;
import java.util.function.Function;

public class EzyEquals<T> {

	private Function<T, Object>[] functions;
	
	@SuppressWarnings("unchecked")
	public boolean isEquals(T thiz, Object other) {
		if(other == null)
			return false;
		if(other == thiz)
			return true;
		if(!other.getClass().equals(thiz.getClass()))
			return false;
		T t = (T)other;
		for(Function<T, Object> func : functions) {
			Object v1 = func.apply(t);
			Object v2 = func.apply(thiz);
			if(v1 == v2)
				continue;
			if(v1 == null)
				return false;
			if(v2 == null)
				return false;
			if(!v1.equals(v2))
				return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public EzyEquals<T> function(Function<T, Object> function) {
		if(functions == null) {
			functions = new Function[1];
		}
		else {
			Arrays.copyOf(functions, functions.length + 1);
		}
		functions[functions.length - 1] = function;
		return this;
	}
	
}
