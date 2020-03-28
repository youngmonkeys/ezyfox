package com.tvd12.ezyfox.tool;

import com.tvd12.ezyfox.io.EzyStrings;

public final class EzyStringTool {

	public static final String TAB_4_SPACES = "    ";
	
	private EzyStringTool() {}
	
	public static String tab(String origin, int ntabs) {
		String answer = tab(origin, ntabs, false);
		return answer;
	}
	
	public static String tab(String origin, int ntabs, boolean tab4spaces) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0 ; i < ntabs ; ++i)
			builder.append("\t");
		String after = builder.append(origin).toString();
		String answer = after;
		if(tab4spaces)
			answer = after.replace("\t", TAB_4_SPACES);
		return answer;
	}
	
	public static String tabAll(String origin, int ntabs) {
		String answer = tabAll(origin, ntabs, false);
		return answer;
	}
	
	public static String tabAll(String origin, int ntabs, boolean tab4spaces) {
		String[] strs = origin.split("\n");
		StringBuilder builder = new StringBuilder();
		int lastIndex = strs.length - 1;
		for(int i = 0 ; i < strs.length ; ++i) {
			builder.append(tab(strs[i], ntabs, tab4spaces));
			if(i < lastIndex)
				builder.append("\n");
		}
		String answer = builder.toString();
		return answer;
	}
	
	public static String upperFistChar(String str) {
		if(EzyStrings.isNoContent(str))
			throw new IllegalArgumentException("input string is null or empty");
		if(str.length() == 1)
			return str.toUpperCase();
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
}
