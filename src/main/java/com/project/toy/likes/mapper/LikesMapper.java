package com.project.toy.likes.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.toy.likes.dto.LikesDTO;

@Mapper
public interface LikesMapper {

	public int selectLikes(LikesDTO params);
	public int findLikes(LikesDTO params);
	public int saveLikes(LikesDTO params);
	public int deleteLikes(LikesDTO params);
	public Object updateCountLikes(LikesDTO params);
	public int selectTodayLikes(LikesDTO params);
}
