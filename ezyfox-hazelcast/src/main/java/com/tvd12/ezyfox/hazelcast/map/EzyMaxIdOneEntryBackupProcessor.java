package com.tvd12.ezyfox.hazelcast.map;

import static com.tvd12.ezyfox.hazelcast.map.EzyAbstractMaxIdEntryProcessor.processEntry;

import java.io.IOException;
import java.util.Map.Entry;

import com.hazelcast.map.EntryBackupProcessor;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

public class EzyMaxIdOneEntryBackupProcessor implements EntryBackupProcessor<String, Long>, DataSerializable {
	private static final long serialVersionUID = -3802285433756793878L;
	
	public EzyMaxIdOneEntryBackupProcessor() {
	}
	
	@Override
	public void processBackup(Entry<String, Long> entry) {
		processEntry(entry, 1);
	}

	@Override
	public void writeData(ObjectDataOutput out) throws IOException {
	}

	@Override
	public void readData(ObjectDataInput in) throws IOException {
	}

}