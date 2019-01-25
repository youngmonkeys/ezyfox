/**
 * 
 */
package com.tvd12.ezyfox.hazelcast.service;

import java.util.Map.Entry;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.EntryBackupProcessor;
import com.hazelcast.map.EntryProcessor;
import com.tvd12.ezyfox.database.service.EzyMaxIdService;
import com.tvd12.ezyfox.hazelcast.constant.EzyMapNames;

/**
 * @author tavandung12
 *
 */
public class EzyEntryProcessorMaxIdService
		extends EzyAbstractMapService<String, Long>
		implements EzyMaxIdService {
	
	protected final EntryProcessor<String, Long> entryProcessor = newEntryProcessor(1);
	
	public EzyEntryProcessorMaxIdService() {
	}

	public EzyEntryProcessorMaxIdService(HazelcastInstance hazelcastInstance) {
		super(hazelcastInstance);
	}
	
	@Override
	public void loadAll() {
		map.loadAll(false);
	}

	@Override
	public Long incrementAndGet(String key) {
		return (Long)map.executeOnKey(key, entryProcessor);
	}
	
	@Override
	public Long incrementAndGet(String key, int delta) {
		return (Long)map.executeOnKey(key, newEntryProcessor(delta));
	}

	@Override
	protected String getMapName() {
		return EzyMapNames.MAX_ID;
	}
	
	protected EntryProcessor<String, Long> newEntryProcessor(int delta) {
		return new EntryProcessor<String, Long>() {
			private static final long serialVersionUID = 1686114015081982789L;

			@Override
			public Object process(Entry<String, Long> entry) {
				Long current = entry.getValue();
				Long maxId = current != null ? current + delta : delta;
				entry.setValue(maxId);
				return maxId;
			}

			@Override
			public EntryBackupProcessor<String, Long> getBackupProcessor() {
				return new EntryBackupProcessor<String, Long>() {
					private static final long serialVersionUID = -5688794206428635754L;

					@Override
					public void processBackup(Entry<String, Long> entry) {
						process(entry);
					}
				};
			}
		};
	}

}
