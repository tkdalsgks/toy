package com.project.toy.user.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "계정 잠긴 사용자")
@Getter
@Setter
public class LockUserDTO {

	@Schema(description = "사용자 아이디")
	private String userId;
	
	@Schema(description = "로그인 실패 횟수")
	private int failCnt;
	
	@Schema(description = "잠김 여부")
	private String lockYn;
	
	@Schema(description = "잠긴 날짜")
	private LocalDateTime lockDate;
}
