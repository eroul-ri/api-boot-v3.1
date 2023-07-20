package com.eroulri.api.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Getter;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 60)
public class RefreshToken {
	@Id
	private String refreshToken;
	private String loginId;

	public RefreshToken(String refreshToken, String loginId) {
		this.refreshToken = refreshToken;
		this.loginId = loginId;
	}
}
