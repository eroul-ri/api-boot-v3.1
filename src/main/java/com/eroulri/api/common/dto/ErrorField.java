package com.eroulri.api.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorField {
	private String name;
	private String reason;
}
