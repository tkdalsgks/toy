package com.project.toy.user.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CertifiedUserDTO {

	private Long id;
	private String userId;
	private String certifiedCode;
	private String certifiedYn;
	private LocalDateTime IDate;
	private LocalDateTime UDate;
}
