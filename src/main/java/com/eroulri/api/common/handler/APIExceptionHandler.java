package com.eroulri.api.common.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.eroulri.api.common.dto.ErrorField;
import com.eroulri.api.common.dto.ErrorResponse;
import com.eroulri.api.common.utils.MessageUtil;
import com.eroulri.api.contant.ERROR_CODE;
import com.eroulri.api.exception.APIException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class APIExceptionHandler {
	private final MessageUtil messageUtil;

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ExceptionHandler(value = {AccessDeniedException.class})
	public ErrorResponse handleAccessDeniedException(AccessDeniedException exception) {
		String errorCode = ERROR_CODE.FORBIDDEN_ERR.getErrorCode();

		log.error("AccessDenied Exception ErrorCode :{}", errorCode);
		log.error("AccessDenied Exception ErrorMessage :{}", exception.getMessage());

		return ErrorResponse.of(errorCode, messageUtil.getMessage(errorCode));
	}

	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(value = {AuthenticationException.class})
	public ErrorResponse handleAuthenticationException(AuthenticationException exception) {
		String errorCode = ERROR_CODE.UNAUTHORIZED_ERR.getErrorCode();

		log.error("Authentication Exception ErrorCode :{}", errorCode);
		log.error("Authentication Exception ErrorMessage :{}", exception.getMessage());

		return ErrorResponse.of(errorCode, messageUtil.getMessage(errorCode));
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = {APIException.class})
	public ErrorResponse handleAPIException(APIException apiException) {
		String errorCode = apiException.getErrorCode().getErrorCode();
		String errorMessage = messageUtil.getMessage(errorCode);

		log.error("APIException ErrorCode :{}", errorCode);

		if(StringUtils.isEmpty(errorMessage)) {
			errorCode = ERROR_CODE.INTERNAL_SERVER_ERR.getErrorCode();
			errorMessage = messageUtil.getMessage(errorCode);

			log.error("APIException ErrorMessage Empty ");

			return ErrorResponse.of(errorCode, errorMessage);
		}
		log.error("APIException ErrorMessage :{}", errorMessage);

		return ErrorResponse.of(errorCode, errorMessage);
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
	public ErrorResponse handleRuntimeException(Exception exception) {
		String errorCode = ERROR_CODE.INTERNAL_SERVER_ERR.getErrorCode();
		String errorMessage = messageUtil.getMessage(errorCode);

		log.error("Exception ErrorCode :{}", errorCode);
		log.error("Exception ErrorMessage :{}", errorMessage);
		log.error("Exception Real ErrorMessage :{}", exception.getMessage());

		return ErrorResponse.of(errorCode, errorMessage);
	}

	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
	public ErrorResponse handleMethodNotAllowed(HttpRequestMethodNotSupportedException exception) {
		String errorCode = ERROR_CODE.METHOD_NOT_ALLOWED_ERR.getErrorCode();

		log.error("MethodNotAllowed Exception ErrorCode : {}", errorCode);
		log.error("MethodNotAllowed Exception Real ErrorMessage: {}", exception.getMessage());

		return ErrorResponse.of(errorCode, exception.getMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
													  BindingResult bindingResult) {
		String errorCode = ERROR_CODE.BAD_REQUEST_ERR.getErrorCode();

		log.error("MethodArgumentNotValid Exception ErrorCode : {}", errorCode);
		log.error("MethodArgumentNotValid Exception Real ErrorMessage : {}", exception.getMessage());

		return ErrorResponse.of(errorCode,
			messageUtil.getMessage(errorCode), getErrorFields(bindingResult));
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = {NoHandlerFoundException.class, NotFoundException.class})
	public ErrorResponse handleNotFound(Exception exception) {
		String errorCode = ERROR_CODE.NOT_FOUND_ERR.getErrorCode();

		log.error("Resource Not Found ErrorCode : {}", errorCode);
		log.error("Resource Not Found Real ErrorMessage : {}", exception.getMessage());

		return ErrorResponse.of(errorCode,
			messageUtil.getMessage(errorCode));
	}

	private List<ErrorField> getErrorFields(BindingResult bindingResult) {
		return bindingResult.getFieldErrors()
							.stream()
							.map(v -> new ErrorField(v.getField(), v.getDefaultMessage()))
							.collect(Collectors.toList());
	}
}
