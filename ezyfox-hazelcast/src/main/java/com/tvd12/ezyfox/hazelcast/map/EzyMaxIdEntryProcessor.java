package com.tvd12.ezyfox.hazelcast.map;

import java.util.Map.Entry;

import com.hazelcast.map.EntryBackupProcessor;
import com.hazelcast.map.EntryProcessor;

public class EzyMaxIdEntryProcessor 
		extends EzyAbstractMaxIdEntryProcessor
		implements EntryProcessor<String, Long> {
	private static final long serialVersionUID = -3802285433756793878L;
	
	public EzyMaxIdEntryProcessor() {
	}
	
	public EzyMaxIdEntryProcessor(int delta) {
		super(delta);
	}
	
	@Override
	public Object process(Entry<String, Long> entry) {
		return processEntry(entry, delta);
	}

	@Override
	public EntryBackupProcessor<String, Long> getBackupProcessor() {
		return new EzyMaxIdEntryBackupProcessor(delta);
	}

}