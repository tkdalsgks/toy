package com.project.toy.points.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointsRequestDTO {

	private String id;
	private String userId;
	private String pointsCd;
	private String points;
}
