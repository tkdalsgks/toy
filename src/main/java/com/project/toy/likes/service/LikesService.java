package com.project.toy.likes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.toy.likes.dto.LikesDTO;
import com.project.toy.likes.mapper.LikesMapper;

@Service
public class LikesService {
	
	@Autowired
	private LikesMapper likesMapper;

	public int findLikes(LikesDTO params) {
		int findLikes = likesMapper.findLikes(params);
		
		if(findLikes == 0) {
			return 0;
		} else {
			return 1;
		}
	}
}
