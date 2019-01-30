package com.tvd12.ezyfox.hazelcast.map;

import java.io.IOException;
import java.util.Map.Entry;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.DataSerializable;

import lombok.Getter;

@Getter
public abstract class EzyAbstractMaxIdEntryProcessor implements DataSerializable {
	
	protected int delta;
	
	public EzyAbstractMaxIdEntryProcessor() {
	}
	
	public EzyAbstractMaxIdEntryProcessor(int delta) {
		this.delta = delta;
	}
	
	public static long processEntry(Entry<String, Long> entry, int delta) {
		Long current = entry.getValue();
		Long maxId = current != null ? current + delta : delta;
		entry.setValue(maxId);
		return maxId;
	}
	
	@Override
	public void writeData(ObjectDataOutput out) throws IOException {
		out.writeInt(delta);
	}

	@Override
	public void readData(ObjectDataInput in) throws IOException {
		this.delta = in.readInt();
	}
	
}
