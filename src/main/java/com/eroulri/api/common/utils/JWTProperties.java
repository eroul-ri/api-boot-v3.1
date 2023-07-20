package com.eroulri.api.common.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "api.jwt")
public class JWTProperties {
	private String secret;
	private String claimsKey;
	private long expiredIn;
	private long refreshExpiredIn;
}
