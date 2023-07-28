package com.project.toy.points.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.toy.points.dto.PointsRequestDTO;

@Mapper
public interface PointsMapper {

	public int savePoints(PointsRequestDTO params);
	public int loginPoints(PointsRequestDTO params);
}
