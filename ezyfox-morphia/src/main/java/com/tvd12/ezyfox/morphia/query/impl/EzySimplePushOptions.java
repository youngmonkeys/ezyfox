package com.tvd12.ezyfox.morphia.query.impl;

import com.tvd12.ezyfox.database.query.EzyPushOptions;

import xyz.morphia.query.PushOptions;

public final class EzySimplePushOptions implements EzyPushOptions {

	private final PushOptions options;
	
	public EzySimplePushOptions(PushOptions options) {
		this.options = options;
	}
	
	@Override
	public EzyPushOptions position(int position) {
		options.position(position);
		return this;
	}

	@Override
	public EzyPushOptions slice(int slice) {
		options.slice(slice);
		return this;
	}

	@Override
	public EzyPushOptions sort(int sort) {
		options.sort(sort);
		return this;
	}

	@Override
	public EzyPushOptions sort(String field, int direction) {
		options.sort(field, direction);
		return this;
	}

	
	
}
