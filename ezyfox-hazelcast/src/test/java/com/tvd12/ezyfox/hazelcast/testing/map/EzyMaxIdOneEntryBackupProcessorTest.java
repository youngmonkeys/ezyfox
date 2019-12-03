package com.tvd12.ezyfox.hazelcast.testing.map;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.hazelcast.map.EzyMaxIdOneEntryBackupProcessor;
import com.tvd12.ezyfox.util.EzyEntry;
import com.tvd12.test.base.BaseTest;

public class EzyMaxIdOneEntryBackupProcessorTest extends BaseTest {

	@Test
	public void test() throws Exception {
		EzyMaxIdOneEntryBackupProcessor processor = new EzyMaxIdOneEntryBackupProcessor();
		processor.processBackup(new EzyEntry<String, Long>("hello", 1L));
		processor.writeData(null);
		processor.readData(null);
	}
	
}
