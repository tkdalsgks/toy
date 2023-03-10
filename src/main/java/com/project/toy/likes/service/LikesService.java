package com.project.toy.likes.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.toy.likes.dto.LikesDTO;
import com.project.toy.likes.mapper.LikesMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikesService {
	
	private final LikesMapper likesMapper;
	
	public int selectLikes(LikesDTO params) {
		return likesMapper.selectLikes(params);
	}

	public int findLikes(LikesDTO params) {
		int findLikes = likesMapper.findLikes(params);
		
		if(findLikes == 0) {
			return 0;
		} else {
			return 1;
		}
	}
	
	@Transactional
	public boolean saveOrDeleteLikes(LikesDTO params) {
		int queryResult = 0;

		if (params.getBoardId() != null) {
			int likes = likesMapper.findLikes(params);
			
			params.setBoardId(params.getBoardId());
			
			if(likes != 0) {
				queryResult = likesMapper.deleteLikes(params);
			} else {
				queryResult = likesMapper.saveLikes(params);
			}
		}

		return (queryResult >= 1) ? true : false;
	}
	
	public void updateCountLikes(LikesDTO params) {
		likesMapper.updateCountLikes(params);
	}
	
	public int selectTodayLikes(LikesDTO params) {
		return likesMapper.selectTodayLikes(params);
	}
}
