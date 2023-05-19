package com.project.toy.admin.dto;

import java.time.LocalDateTime;

import com.project.toy.user.dto.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
	
	@Schema(description = "번호", nullable = false)
	private String userNo;
	
	@Schema(description = "아이디", nullable = false)
	private String userId;
	
	@Schema(description = "이메일", nullable = false, example = "toy@toy.com")
	private String userEmail;
	
	@Schema(description = "닉네임", nullable = false)
	private String userNickname;
	
	@Schema(description = "가입 구분")
	private String provider;
	
	@Schema(description = "사용 구분")
	private String useYn;
	
	@Schema(description = "가입 일자", defaultValue = "NOW()")
	private LocalDateTime IDate;
	
	@Schema(description = "권한", nullable = false, defaultValue = "ROLE_USER")
	private Role role;
	
	private String modelName;

}
