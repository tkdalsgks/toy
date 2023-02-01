package com.project.toy.user.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LockUserDTO {

	private String userId;			// 사용자 아이디
	private int failCnt;			// 로그인 실패 횟수
	private String lockYn;			// 계정 잠김 여부
	private LocalDateTime lockDate;	// 계정 잠긴 날짜
}
