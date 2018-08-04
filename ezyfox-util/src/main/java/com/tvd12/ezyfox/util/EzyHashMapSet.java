package com.tvd12.ezyfox.util;

import java.util.HashSet;
import java.util.Set;

import com.tvd12.ezyfox.util.EzyHashMapCollection;
import com.tvd12.ezyfox.util.EzyMapSet;

public class EzyHashMapSet<K,E>
		extends EzyHashMapCollection<K, E, Set<E>>
		implements EzyMapSet<K, E> {
	private static final long serialVersionUID = 4067364721031740580L;

	@Override
	protected Set<E> newCollection() {
		return new HashSet<>();
	}
	
}
