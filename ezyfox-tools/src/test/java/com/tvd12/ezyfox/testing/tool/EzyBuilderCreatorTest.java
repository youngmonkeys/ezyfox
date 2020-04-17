package com.tvd12.ezyfox.testing.tool;

import com.tvd12.ezyfox.tool.EzyBuilderCreator;
import com.tvd12.ezyfox.tool.data.EzyKeywordsLine;

public class EzyBuilderCreatorTest {

	public static void main(String[] args) throws Exception {
		EzyBuilderCreator creator = new EzyBuilderCreator();
		System.out.println(creator.create(EzyKeywordsLine.class));
	}
	
	public static class ClassA {
		protected String key;
		protected int value;
	}
	
}
