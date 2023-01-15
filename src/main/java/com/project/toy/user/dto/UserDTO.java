package com.project.toy.user.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	private String userNo;	// 사용자 번호
	private String userId;	// 사용자 아이디
	private String userPwd;	// 사용자 암호
	private String userEmail;	// 사용자 이메일
	private String userNickname;	// 사용자 닉네임
	private String provider;		// 가입 구분
	private LocalDateTime IDate;	// 가입일자
	private Role role;
	
	@Builder
	public UserDTO(String userId, String userPwd, String userEmail, String userNickname, String provider, Role role) {
		this.userId = userId;
		this.userPwd = userPwd;
		this.userEmail = userEmail;
		this.userNickname = userNickname;
		this.provider = provider;
		this.role = role;
	}
	
	public UserDTO update(String userPwd, String userNickname, String userEmail) {
		this.userPwd = userPwd;
		this.userNickname = userNickname;
		this.userEmail = userEmail;
		
		return this;
	}
	
	public String getRoleKey() {
		return this.getRole().getKey();
	}
}
