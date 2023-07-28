package com.project.toy.points.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class PointsResponseDTO {

	private String id;
	private String pointsCd;
	private String points;
	private String userId;
	private LocalDateTime IDate;
}
