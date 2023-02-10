package com.project.toy.common.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginLogDTO {

	private String loginSeq;
	private String loginId;
	private String accessIp;
	private LocalDateTime loginDate;
}
