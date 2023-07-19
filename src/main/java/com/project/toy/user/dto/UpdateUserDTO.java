package com.project.toy.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDTO {

	private String userId;
	private String userNickname;
	private String userEmail;
	private String role;
}
