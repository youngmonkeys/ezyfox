package com.tvd12.ezyfox.function;

import java.util.function.Predicate;

@SuppressWarnings("rawtypes")
public final class EzyPredicates {

	public static final Predicate ALWAY_TRUE = o -> true;
	
	private EzyPredicates() {
	}
	
	@SuppressWarnings("unchecked")
	public static <T> Predicate<T> alwayTrue() {
		return ALWAY_TRUE;
	}
	
}
