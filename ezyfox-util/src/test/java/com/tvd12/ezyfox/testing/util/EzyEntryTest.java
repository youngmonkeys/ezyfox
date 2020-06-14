package com.tvd12.ezyfox.testing.util;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.util.EzyEntry;
import com.tvd12.test.base.BaseTest;

public class EzyEntryTest extends BaseTest {

	@Test
	public void test() {
		EzyEntry<String, String> ab = EzyEntry.of("a", "b");
		
		assert ab.equals(ab);
		assert ab.getKey().equals("a");
		assert ab.getValue().equals("b");
		
		EzyEntry<String, String> bc = new EzyEntry<>();
		bc.setKey("b");
		assert bc.setValue("c") == null;
		assert !ab.equals(bc);
		assert ab.hashCode() != bc.hashCode();
		assert !ab.equals(null);
		assert !ab.equals((Object)new String());
		
		EzyEntry<String, String> abe = EzyEntry.of("a", "b");
		assert ab.equals(abe);
		assert ab.hashCode() == abe.hashCode();
	}
	
}
