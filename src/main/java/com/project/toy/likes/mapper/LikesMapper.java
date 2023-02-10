package com.project.toy.likes.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.toy.likes.dto.LikesDTO;

@Mapper
public interface LikesMapper {

	public int findLikes(LikesDTO params);

}
