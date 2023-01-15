package com.project.toy.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	
	// 관리자
	ADMIN("ROLE_ADMIN", "관리자"),
	
	// 정상 인증된 회원
	USER("ROLE_USER", "인증 회원"),
	
	// 탈퇴 및 추방 회원
	UN_USER("ROLE_UN_USER", "탈퇴 회원"),
	
	// 미인증 회원
	GUEST("ROLE_GUEST", "미인증 회원");
	
	private final String key;
	private final String title;
}
