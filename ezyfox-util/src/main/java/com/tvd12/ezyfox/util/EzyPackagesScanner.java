package com.tvd12.ezyfox.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unchecked")
public abstract class EzyPackagesScanner<T extends EzyPackagesScanner<T>>
		extends EzyPropertiesKeeper<T> {

	protected final Set<String> packagesToScan = new HashSet<>();
	
	public T scan(String packageToScan) {
		this.packagesToScan.add(packageToScan);
		return (T)this;
	}
	
	public T scan(String... packagesToScan) {
		return scan(Arrays.asList(packagesToScan));
	}
	
	public T scan(Iterable<String> packagesToScan) {
		for(String packageToScan : packagesToScan)
			scan(packageToScan);
		return (T)this;
	}
}
