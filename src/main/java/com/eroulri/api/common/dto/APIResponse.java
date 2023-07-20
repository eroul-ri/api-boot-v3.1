package com.eroulri.api.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class APIResponse<T> {
	private String code;
	private String message;

	private T data;

	@Builder
	public APIResponse(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}
}
