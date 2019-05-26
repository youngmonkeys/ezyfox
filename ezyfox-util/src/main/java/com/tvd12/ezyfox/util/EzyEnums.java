package com.tvd12.ezyfox.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.tvd12.ezyfox.constant.EzyConstant;
import com.tvd12.ezyfox.constant.EzyHasIntId;

public final class EzyEnums {

	private EzyEnums() {
	}
	
	public static <T extends EzyHasIntId> T valueOf(T[] values, int id) {
		return valueOf(values, id, v -> v.getId());
	}
	
	public static <T> T valueOf(
			T[] values, Object id, Function<T, Object> idFetcher) {
		for(T v : values) {
			Object vid = idFetcher.apply(v);
            if(vid.equals(id)) {
                return v;
            }
		}
        throw new IllegalArgumentException("has no enum value with id = " + id);
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends EzyConstant> Map<Integer, E> enumMapInt(
			Class<E> enumClass) {
		Object[] values = enumClass.getEnumConstants();
		Map<Integer, E> answer = new HashMap<>();
		for(Object value : values) {
			EzyConstant c = (EzyConstant)value;
			answer.put(c.getId(), (E)value);
		}
		return answer;
	}
}
