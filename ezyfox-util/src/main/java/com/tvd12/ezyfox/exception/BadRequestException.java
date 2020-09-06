package com.tvd12.ezyfox.exception;

import lombok.Getter;

public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 3238679251496900960L;
	
	@Getter
	protected final int code;

	public BadRequestException() {
		this.code = 0;
	}

	public BadRequestException(Exception e) {
		super("BadRequestException", e);
		this.code = 0;
	}

	public BadRequestException(int code, String message) {
		super(message);
		this.code = code;
	}

	public BadRequestException(int code, String message, Exception e) {
		super(message, e);
		this.code = code;
	}

}
