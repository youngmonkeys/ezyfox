package com.tvd12.ezyfox.hazelcast.map;

import java.util.Map.Entry;

import com.hazelcast.map.EntryBackupProcessor;

public class EzyMaxIdEntryBackupProcessor 
		extends EzyAbstractMaxIdEntryProcessor
		implements EntryBackupProcessor<String, Long> {
	private static final long serialVersionUID = 7382606689001032899L;
	
	public EzyMaxIdEntryBackupProcessor() {
	}
	
	public EzyMaxIdEntryBackupProcessor(int delta) {
		super(delta);
	}
	
	@Override
	public void processBackup(Entry<String, Long> entry) {
		processEntry(entry, delta);
	}

}