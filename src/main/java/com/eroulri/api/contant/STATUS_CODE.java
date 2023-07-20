package com.eroulri.api.contant;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum STATUS_CODE {
	SUCCESS(HttpStatus.OK, "200", "Success"),
	FAIL(HttpStatus.OK, "9999", "FAIL");

	private HttpStatus status;
	private String code;
	private String message;
}
