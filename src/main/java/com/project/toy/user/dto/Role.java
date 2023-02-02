package com.project.toy.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	
	@Schema(description = "관리자")
	ADMIN("ROLE_ADMIN", "관리자"),
	
	@Schema(description = "정상 인증된 회원")
	USER("ROLE_USER", "인증 회원"),
	
	@Schema(description = "탈퇴 및 추방된 회원")
	UN_USER("ROLE_UN_USER", "탈퇴 및 추방 회원"),
	
	@Schema(description = "미인증 회원")
	GUEST("ROLE_GUEST", "미인증 회원");
	
	private final String key;
	private final String title;
}
