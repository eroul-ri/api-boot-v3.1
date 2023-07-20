package com.eroulri.api.common.filter;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eroulri.api.common.utils.JWTProvider;
import com.eroulri.api.contant.TOKEN_TYPE;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	private final JWTProvider jwtProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {
		String authorization = request.getHeader("Authorization");

		if(StringUtils.isEmpty(authorization)) {
			filterChain.doFilter(request, response);
			return;
		}

		// @TODO eroul-ri Header username, password parse
		String basic = authorization.substring(6);
		if(StringUtils.isEmpty(basic)) {
			// @TODO eroul-ri validation fail
		}

		if(authorization.startsWith(TOKEN_TYPE.Bearer.name())) {
			String accessToken = authorization.substring(7);
			if(StringUtils.isNotEmpty(accessToken) && jwtProvider.isValidToken(accessToken)) {
				Authentication authentication = jwtProvider.getAuthentication(accessToken);
				// 인증완료 처리
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		filterChain.doFilter(request, response);
	}
}
