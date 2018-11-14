package com.tvd12.ezyfox.morphia.query.impl;

import java.util.List;

import com.tvd12.ezyfox.database.query.EzyPushOptions;
import com.tvd12.ezyfox.database.query.EzyUpdateOperations;
import com.tvd12.ezyfox.function.EzyApply;

import xyz.morphia.query.PushOptions;
import xyz.morphia.query.UpdateOperations;

public final class EzySimpleUpdateOperations<T> implements EzyUpdateOperations<T> {

	private final UpdateOperations<T> operations;
	
	public EzySimpleUpdateOperations(UpdateOperations<T> operations) {
		this.operations = operations;
	}

	@Override
	public EzyUpdateOperations<T> addToSet(String field, Object value) {
		operations.addToSet(field, value);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> addToSet(String field, List<?> values) {
		operations.addToSet(field, values);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> dec(String field) {
		operations.dec(field);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> dec(String field, Number value) {
		operations.dec(field, value);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> disableValidation() {
		operations.disableValidation();
		return this;
	}

	@Override
	public EzyUpdateOperations<T> enableValidation() {
		operations.enableValidation();
		return this;
	}

	@Override
	public EzyUpdateOperations<T> inc(String field) {
		operations.inc(field);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> inc(String field, Number value) {
		operations.inc(field, value);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> isolated() {
		operations.isolated();
		return this;
	}

	@Override
	public boolean isIsolated() {
		return operations.isIsolated();
	}

	@Override
	public EzyUpdateOperations<T> max(String field, Number value) {
		operations.max(field, value);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> min(String field, Number value) {
		operations.min(field, value);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> push(String field, Object value) {
		operations.push(field, value);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> push(String field, Object value, EzyApply<EzyPushOptions> options) {
		PushOptions real = new PushOptions();
		EzyPushOptions proxy = new EzySimplePushOptions(real);
		options.apply(proxy);
		operations.push(field, value, real);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> push(String field, List<?> values) {
		operations.push(field, values);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> push(String field, List<?> values, EzyApply<EzyPushOptions> options) {
		PushOptions real = new PushOptions();
		EzyPushOptions proxy = new EzySimplePushOptions(real);
		options.apply(proxy);
		operations.push(field, values, real);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> removeAll(String field, Object value) {
		operations.removeAll(field, value);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> removeAll(String field, List<?> values) {
		operations.removeAll(field, values);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> removeFirst(String field) {
		operations.removeFirst(field);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> removeLast(String field) {
		operations.removeLast(field);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> set(String field, Object value) {
		operations.set(field, value);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> setOnInsert(String field, Object value) {
		operations.setOnInsert(field, value);
		return this;
	}

	@Override
	public EzyUpdateOperations<T> unset(String field) {
		operations.unset(field);
		return this;
	}
	
	
	
}
