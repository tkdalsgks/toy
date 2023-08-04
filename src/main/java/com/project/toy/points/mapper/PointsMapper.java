package com.project.toy.points.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.toy.points.dto.PointsRequestDTO;
import com.project.toy.points.dto.PointsResponseDTO;
import com.project.toy.points.dto.RankDTO;

@Mapper
public interface PointsMapper {

	public int savePoints(PointsRequestDTO params);
	public int loginPoints(PointsRequestDTO params);
	public PointsResponseDTO expirePoints(String userId);
	public List<PointsResponseDTO> earningsPoints(String userId);
	public List<RankDTO> rankingTotalPoints();
	public List<RankDTO> rankingWeekPoints();
}
