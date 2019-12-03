package com.tvd12.ezyfox.hazelcast.testing.map;

import org.testng.annotations.Test;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.tvd12.ezyfox.hazelcast.map.EzyAbstractMaxIdEntryProcessor;
import com.tvd12.test.base.BaseTest;
import static org.mockito.Mockito.*;

public class EzyAbstractMaxIdEntryProcessorTest extends BaseTest {

	@Test
	public void test() throws Exception {
		ExEzyAbstractMaxIdEntryProcessor processor = new ExEzyAbstractMaxIdEntryProcessor();
		assert processor.getDelta() == 0;
		ObjectDataOutput out = mock(ObjectDataOutput.class);
		processor.writeData(out);
		ObjectDataInput in = mock(ObjectDataInput.class);
		when(in.readInt()).thenReturn(123);
		processor.readData(in);
		assert processor.getDelta() == 123;
	}
	
	public static class ExEzyAbstractMaxIdEntryProcessor extends EzyAbstractMaxIdEntryProcessor {
	}
	
}
