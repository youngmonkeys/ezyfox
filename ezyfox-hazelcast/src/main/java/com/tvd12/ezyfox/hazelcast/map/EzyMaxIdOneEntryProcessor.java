package com.tvd12.ezyfox.hazelcast.map;

import java.io.IOException;
import java.util.Map.Entry;

import com.hazelcast.map.EntryBackupProcessor;
import com.hazelcast.map.EntryProcessor;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;
import static com.tvd12.ezyfox.hazelcast.map.EzyAbstractMaxIdEntryProcessor.*;

public class EzyMaxIdOneEntryProcessor implements EntryProcessor<String, Long>, DataSerializable {
	private static final long serialVersionUID = -3802285433756793878L;
	
	@Override
	public Object process(Entry<String, Long> entry) {
		return processEntry(entry, 1);
	}

	@Override
	public EntryBackupProcessor<String, Long> getBackupProcessor() {
		return new EzyMaxIdOneEntryBackupProcessor();
	}

	@Override
	public void writeData(ObjectDataOutput out) throws IOException {
	}

	@Override
	public void readData(ObjectDataInput in) throws IOException {
	}

}