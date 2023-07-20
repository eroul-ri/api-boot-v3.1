package com.eroulri.api.exception;

import com.eroulri.api.contant.ERROR_CODE;

import lombok.Getter;

@Getter
public class APIException extends RuntimeException {
	private ERROR_CODE errorCode;

	public APIException(ERROR_CODE errorCode) {
		this.errorCode = errorCode;
	}
}
