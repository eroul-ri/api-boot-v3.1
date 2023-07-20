package com.eroulri.api.common.handler;

import java.io.IOException;
import java.io.OutputStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
@Component
@RequiredArgsConstructor
public class APIAccessDeniedHandler implements AccessDeniedHandler {
	private final MessageUtil messageUtil;
	private final ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
					   AccessDeniedException accessDeniedException) throws IOException, ServletException {

		String errorCode = ERROR_CODE.FORBIDDEN_ERR.getErrorCode();
		ErrorResponse errorResponse = ErrorResponse.of(errorCode, messageUtil.getMessage(errorCode));

		log.error("AccessDenied Exception ErrorMessage : {}", accessDeniedException.getMessage());

		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);

		OutputStream outputStream = response.getOutputStream();

		objectMapper.writeValue(outputStream, errorResponse);

		outputStream.flush();
	}
}
