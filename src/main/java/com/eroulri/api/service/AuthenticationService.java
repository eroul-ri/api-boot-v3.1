package com.eroulri.api.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.eroulri.api.common.utils.JWTProvider;
import com.eroulri.api.dto.TokenInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private final JWTProvider jwtProvider;

	public TokenInfo.AccessToken generateAccessToken(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			username, password);

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		return jwtProvider.generateToken(authentication);
	}
}