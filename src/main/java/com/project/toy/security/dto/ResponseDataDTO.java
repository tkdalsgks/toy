package com.project.toy.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "에러 응답")
@Getter
@Setter
public class ResponseDataDTO {

	@Schema(description = "코드")
	private String code;
	
	@Schema(description = "상태")
	private String status;
	
	@Schema(description = "내용")
	private String message;
}
