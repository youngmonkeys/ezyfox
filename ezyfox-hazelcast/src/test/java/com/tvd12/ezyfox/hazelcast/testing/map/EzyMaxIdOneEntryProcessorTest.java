package com.tvd12.ezyfox.hazelcast.testing.map;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.hazelcast.map.EzyMaxIdOneEntryProcessor;
import com.tvd12.test.base.BaseTest;

public class EzyMaxIdOneEntryProcessorTest extends BaseTest {

	@Test
	public void test() throws Exception {
		EzyMaxIdOneEntryProcessor processor = new EzyMaxIdOneEntryProcessor();
		processor.writeData(null);
		processor.readData(null);
	}
	
}
