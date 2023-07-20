package com.eroulri.api.common.utils;

import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class Base64Util {
	public static String encode(String str) {
		return Base64.getEncoder().encodeToString(str.getBytes());
	}

	public static String decode(String encodeStr) {
		return new String(Base64.getDecoder().decode(encodeStr));
	}
}
