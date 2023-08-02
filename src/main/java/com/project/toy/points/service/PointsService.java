package com.project.toy.points.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.toy.points.dto.PointsRequestDTO;
import com.project.toy.points.dto.PointsResponseDTO;
import com.project.toy.points.mapper.PointsMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointsService {
	
	private final PointsMapper pointsMapper;

	@Transactional
	public boolean savePoints(PointsRequestDTO params) {
		int queryResult = 0;

		if (params.getId() == null) {
			queryResult = pointsMapper.savePoints(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Transactional
	public boolean selectPoints(PointsRequestDTO params) {
		int queryResult = 0;
		boolean result = false;
		
		if (params.getId() == null) {
			if("1".equals(params.getPointsCd())) {	// 하루 단위 첫 로그인시 100포인트
				queryResult = pointsMapper.loginPoints(params);
				result = (queryResult >= 1) ? false : true;
				
				if(result == true) {
					pointsMapper.savePoints(params);
				}
				
				return result;
			} else if("2".equals(params.getPointsCd())) {	// 게시글 작성 시 100포인트
				queryResult = pointsMapper.savePoints(params);
				result = (queryResult == 1) ? true : false;
				
				return result;
			} else if("3".equals(params.getPointsCd())) {	// 댓글 작성 시 10포인트
				queryResult = pointsMapper.savePoints(params);
				result = (queryResult == 1) ? true : false;
				
				return result;
			} else if("4".equals(params.getPointsCd())) {	// 채팅 입력 시 1포인트
				queryResult = pointsMapper.savePoints(params);
				result = (queryResult == 1) ? true : false;
				
				return result;
			}
		}
		
		return result;
	}
	
	public PointsResponseDTO expirePoints(String userId) {
		return pointsMapper.expirePoints(userId);
	}
	
	public List<PointsResponseDTO> earningsPoints(String userId) {
		return pointsMapper.earningsPoints(userId);
	}
}
