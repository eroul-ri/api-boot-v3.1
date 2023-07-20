package com.eroulri.api.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eroulri.api.common.dto.APIResponse;
import com.eroulri.api.common.utils.Base64Util;
import com.eroulri.api.contant.STATUS_CODE;
import com.eroulri.api.contant.TOKEN_TYPE;
import com.eroulri.api.dto.TokenInfo;
import com.eroulri.api.service.AuthenticationService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "02. User")
@RequiredArgsConstructor
@RestController
public class AuthenticationController {
	private final AuthenticationService authenticationService;

	@GetMapping("/v1/authentication")
	public ResponseEntity<APIResponse<TokenInfo.Response>> authentication(HttpServletRequest request) {
		String authorization = request.getHeader("Authorization");
		if(StringUtils.isEmpty(authorization) || !authorization.startsWith(TOKEN_TYPE.Basic.name())) {
			// @TODO eroul-ri validation fail 잘못된 요청 처리
		}
		String basic = Base64Util.decode(authorization.substring(6));

		String[] userInfo = basic.split(":");
		if(userInfo.length != 2) {
			// @TODO eroul-ri validation fail 잘못된 요청 처리
		}
		TokenInfo.AccessToken accessToken = authenticationService.generateAccessToken(userInfo[0], userInfo[1]);
		// 인증처리
		return ResponseEntity.ok(APIResponse.<TokenInfo.Response>builder()
											.code(STATUS_CODE.SUCCESS.getCode())
											.message(STATUS_CODE.SUCCESS.getMessage())
											.data(new TokenInfo.Response(accessToken))
											.build());
	}

	@GetMapping("/v1/authentication/refresh")
	public ResponseEntity<APIResponse<TokenInfo.AccessToken>> refreshAccessToken(HttpServletRequest request) {
		return null;
	}
}
