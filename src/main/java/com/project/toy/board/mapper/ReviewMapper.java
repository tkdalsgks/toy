package com.project.toy.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.toy.board.dto.ReviewRequestDTO;
import com.project.toy.board.dto.ReviewResponseDTO;
import com.project.toy.common.dto.SearchDTO;
import com.project.toy.likes.dto.LikesDTO;

@Mapper
public interface ReviewMapper {

	public List<ReviewResponseDTO> findAll(SearchDTO params);
	public List<ReviewResponseDTO> findNotice(SearchDTO params);
	public List<LikesDTO> findLikesBestReview(SearchDTO params);
	public ReviewResponseDTO findByReviewId(Long id);
	public void saveReview(ReviewRequestDTO params);
	public void updateReview(ReviewRequestDTO params);
	public void deleteByReviewId(Long id);
	public int countReview(SearchDTO params);
	public void countHits(Long id);
}
