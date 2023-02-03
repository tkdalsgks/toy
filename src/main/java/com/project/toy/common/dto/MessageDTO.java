package com.project.toy.common.dto;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMethod;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "사용자에게 전달할 메세지")
@Getter
@AllArgsConstructor
public class MessageDTO {

	@Schema(description = "내용")
	private String message;
	
	@Schema(description = "리다이렉트 URI")
	private String redirectUri;
	
	@Schema(description = "HTTP 요청 메서드")
	private RequestMethod method;
	
	@Schema(description = "화면으로 전달할 데이터")
	private Map<String, Object> data;
}
