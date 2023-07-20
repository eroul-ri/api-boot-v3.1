package com.eroulri.api.contant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ERROR_CODE {
	INTERNAL_SERVER_ERR("E5000"),
	METHOD_NOT_ALLOWED_ERR("E4050"),
	BAD_REQUEST_ERR("E4000"),
	NOT_FOUND_ERR("E4040"),
	UNAUTHORIZED_ERR("E4010"),
	FORBIDDEN_ERR("E4030");

	private final String errorCode;
}
