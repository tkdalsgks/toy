package com.project.toy.user.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class SessionUser implements Serializable {

	private String userNo;	// 사용자 번호
	private String userId;	// 사용자 아이디
	private String userEmail;	// 사용자 이메일
	private String userNickname;	// 사용자 닉네임
	private LocalDateTime IDate;	// 가입일자
	private Role role;
	
	public SessionUser(UserDTO userDTO) {
		this.userNo = userDTO.getUserNo();
		this.userId = userDTO.getUserId();
		this.userEmail = userDTO.getUserEmail();
		this.userNickname = userDTO.getUserNickname();
		this.IDate = userDTO.getIDate();
		this.role = userDTO.getRole();
	}
}
