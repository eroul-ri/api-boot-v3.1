package com.eroulri.api.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.eroulri.api.contant.TOKEN_TYPE;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Getter;

public class TokenInfo {

	@Getter
	public static class AccessToken {
		private String accessToken;
		private String refreshToken;
		private TOKEN_TYPE tokenType;

		private Date expiredIn;

		@Builder
		public AccessToken(String accessToken, String refreshToken, TOKEN_TYPE tokenType, Date expiredIn) {
			this.accessToken = accessToken;
			this.refreshToken = refreshToken;
			this.tokenType = tokenType;
			this.expiredIn = expiredIn;
		}
	}

	@Getter
	public static class Response {
		private String accessToken;
		private String refreshToken;
		private String tokenType;

		@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
		@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
		private LocalDateTime expiredIn;

		public Response(AccessToken accessToken) {
			this.accessToken = accessToken.getAccessToken();
			this.refreshToken = accessToken.getRefreshToken();
			this.tokenType = accessToken.getTokenType().name();
			this.expiredIn = accessToken.getExpiredIn()
										.toInstant()
										.atZone(ZoneId.systemDefault())
										.toLocalDateTime();
		}
	}
}
