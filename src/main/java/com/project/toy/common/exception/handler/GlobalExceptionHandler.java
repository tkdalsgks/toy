package com.project.toy.common.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.toy.common.exception.CustomException;
import com.project.toy.common.exception.dto.ErrorCode;
import com.project.toy.common.exception.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<ErrorResponse> handleCustomException(final CustomException e) {
		log.error("handleCustomException: {}", e.getErrorCode());
		return ErrorResponse.toResponseEntity(e.getErrorCode());
	}
	
	/**
	 * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
	 * @param e
	 * @return
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ErrorResponse> handlehttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
		log.error("handleHttpRequestMethodNotSupportedException: {}", e.getMessage());
		return ErrorResponse.toResponseEntity(ErrorCode.METHOD_NOT_ALLOWED);
	}
	
	/**
	 * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ErrorResponse> handleException(final Exception e) {
		log.error("handleException: {}", e.getMessage());
		return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
	}
}
