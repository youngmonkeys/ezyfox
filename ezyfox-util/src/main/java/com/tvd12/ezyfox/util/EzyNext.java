package com.tvd12.ezyfox.util;

public class EzyNext extends Next {
	
	public EzyNext(long skip, long limit) {
		super(skip, limit);
	}
	
	public static EzyNext fromSkipLimit(long skip, long limit) {
		return new EzyNext(skip, limit);
	}
	
	/**
	 * @param page the page start from 0
	 * @param size the page size
	 * @return the Next object
	 */
	public static EzyNext fromPageSize(long page, long size) {
		return new EzyNext((page < 0L ? 0L : page) * size, size);
	}
	
}
