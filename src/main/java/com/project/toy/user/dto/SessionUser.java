package com.project.toy.user.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "세션에 담을 사용자")
@SuppressWarnings("serial")
@Getter
@Setter
public class SessionUser implements Serializable {

	@Schema(description = "번호", nullable = false)
	private String userNo;
	
	@Schema(description = "아이디", nullable = false)
	private String userId;
	
	@Schema(description = "이메일", nullable = false, example = "toy@toy.com")
	private String userEmail;
	
	@Schema(description = "닉네임", nullable = false)
	private String userNickname;
	
	@Schema(description = "가입일자", defaultValue = "NOW()")
	private LocalDateTime IDate;
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
