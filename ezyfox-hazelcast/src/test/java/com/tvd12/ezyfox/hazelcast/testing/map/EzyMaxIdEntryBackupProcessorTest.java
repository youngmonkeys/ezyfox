package com.tvd12.ezyfox.hazelcast.testing.map;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.hazelcast.map.EzyMaxIdEntryBackupProcessor;
import com.tvd12.ezyfox.util.EzyEntry;
import com.tvd12.test.base.BaseTest;

public class EzyMaxIdEntryBackupProcessorTest extends BaseTest {

	@Test
	public void test() {
		EzyMaxIdEntryBackupProcessor processor = new EzyMaxIdEntryBackupProcessor();
		processor.processBackup(new EzyEntry<String, Long>("hello", 120L));
	}
	
}
