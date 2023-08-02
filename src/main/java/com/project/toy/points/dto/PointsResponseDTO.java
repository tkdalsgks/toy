package com.project.toy.points.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class PointsResponseDTO {

	private String id;
	private String userId;
	private String userNickname;
	private String pointsCd;
	private String points;
	private LocalDateTime IDate;
	
	private String commDNm;
}
