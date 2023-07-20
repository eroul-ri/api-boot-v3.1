package com.eroulri.api.exception;

import com.eroulri.api.contant.INVALID_REASON;

public class InvalidTokenException extends RuntimeException {
	private INVALID_REASON invalidReason;
	private String message;

	public InvalidTokenException(INVALID_REASON invalidReason) {
		this.invalidReason = invalidReason;
	}

	public InvalidTokenException(INVALID_REASON invalidReason, String message) {
		super(message);
		this.invalidReason = invalidReason;
	}
}
