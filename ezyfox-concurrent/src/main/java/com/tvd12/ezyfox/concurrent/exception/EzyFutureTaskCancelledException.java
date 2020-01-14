package com.tvd12.ezyfox.concurrent.exception;

public class EzyFutureTaskCancelledException extends RuntimeException {
	private static final long serialVersionUID = 7012377646935511890L;

	public EzyFutureTaskCancelledException(String message) {
		super(message);
	}
	
}
