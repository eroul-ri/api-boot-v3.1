package com.eroulri.api.contant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MEMBER_TYPE {
	ADMIN("ADMIN", "관리자"),
	CLIENT("CLIENT", "클라이언트");

	private String code;
	private String value;
}
