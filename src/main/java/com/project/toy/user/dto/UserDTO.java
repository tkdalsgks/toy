package com.project.toy.user.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "사용자")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	@Schema(description = "번호", nullable = false)
	private String userNo;
	
	@Schema(description = "아이디", nullable = false)
	private String userId;
	
	@Schema(description = "비밀번호")
	private String userPwd;
	
	@Schema(description = "이메일", nullable = false, example = "toy@toy.com")
	private String userEmail;
	
	@Schema(description = "닉네임", nullable = false)
	private String userNickname;
	
	@Schema(description = "가입 구분")
	private String provider;
	
	private String lockYn;
	
	@Schema(description = "사용 구분")
	private String useYn;
	
	@Schema(description = "가입 일자", defaultValue = "NOW()")
	private LocalDateTime IDate;
	
	@Schema(description = "정보변경 일자", defaultValue = "NOW()")
	private LocalDateTime UDate;
	
	@Schema(description = "패스워드 변경 일자", defaultValue = "NOW()")
	private LocalDateTime pwdUDate;
	
	@Schema(description = "권한", nullable = false, defaultValue = "ROLE_USER")
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
