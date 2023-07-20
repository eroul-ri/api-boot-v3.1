package com.eroulri.api.common.handler;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.eroulri.api.common.dto.ErrorResponse;
import com.eroulri.api.common.utils.MessageUtil;
import com.eroulri.api.contant.ERROR_CODE;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class APIAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private final MessageUtil messageUtil;
	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
						 AuthenticationException authException) throws IOException, ServletException {
		String errorCode = ERROR_CODE.UNAUTHORIZED_ERR.getErrorCode();
		ErrorResponse errorResponse = ErrorResponse.of(errorCode, messageUtil.getMessage(errorCode));

		log.error("Authentication Exception ErrorMessage : {}", authException.getMessage());

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		OutputStream outputStream = response.getOutputStream();

		objectMapper.writeValue(outputStream, errorResponse);

		outputStream.flush();
	}
}
