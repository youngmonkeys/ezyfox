package com.tvd12.ezyfox.util;

import java.util.ArrayList;
import java.util.List;

import com.tvd12.ezyfox.util.EzyHashMapCollection;
import com.tvd12.ezyfox.util.EzyMapList;

public class EzyHashMapList<K,E>
		extends EzyHashMapCollection<K, E, List<E>>
		implements EzyMapList<K, E> {
	private static final long serialVersionUID = 3678081740856760565L;

	@Override
	protected List<E> newCollection() {
		return new ArrayList<>();
	}
	
}
