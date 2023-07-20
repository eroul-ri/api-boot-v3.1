package com.eroulri.api.contant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum INVALID_REASON {
	EXPIRED, MALFORMED, UNSUPPORTED, CLAIMS, AUTHORITY, ETC
}
