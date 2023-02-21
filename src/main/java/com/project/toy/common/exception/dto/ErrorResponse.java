package com.project.toy.common.exception.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.ResponseEntity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {

	private final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/DD HH:mm:dd"));
	private final int status;
	private final String error;
	private final String code;
	private final String message;
	
	 public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
	        return ResponseEntity
	                .status(errorCode.getStatus())
	                .body(ErrorResponse.builder()
	                        .status(errorCode.getStatus().value())
	                        .error(errorCode.getStatus().name())
	                        .code(errorCode.name())
	                        .message(errorCode.getMessage())
	                        .build()
	                );
	    }
}
