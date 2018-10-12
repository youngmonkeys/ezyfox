package com.tvd12.ezyfox.testing.entity;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.entity.EzyArrayList;
import com.tvd12.ezyfox.io.EzyCollectionConverter;
import com.tvd12.ezyfox.io.EzyInputTransformer;
import com.tvd12.ezyfox.io.EzyOutputTransformer;
import com.tvd12.test.base.BaseTest;

public class EzyArrayListTest extends BaseTest {
	
	@Test
	public void test() {
		EzyArrayList arr = new EzyEzyArrayList(null, null, null);
		arr.add((Object)null);
		arr.add("a");
		assert arr.isNotNullValue(1);
		assert !arr.isNotNullValue(0);
		assert !arr.isNotNullValue(100);
	}

	public static class EzyEzyArrayList extends EzyArrayList {
		private static final long serialVersionUID = 1L;
		
		public EzyEzyArrayList(
				EzyInputTransformer inputTransformer,
		        EzyOutputTransformer outputTransformer, 
		        EzyCollectionConverter collectionConverter) {
			super(inputTransformer, outputTransformer, collectionConverter);
		}
		
		@Override
		public void add(Object item) {
			list.add(item);
		}
		
	}
	
}
