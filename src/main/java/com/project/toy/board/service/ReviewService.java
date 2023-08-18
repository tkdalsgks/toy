package com.project.toy.board.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.toy.board.dto.ReviewFilterDTO;
import com.project.toy.board.dto.ReviewRequestDTO;
import com.project.toy.board.dto.ReviewResponseDTO;
import com.project.toy.board.mapper.ReviewMapper;
import com.project.toy.common.dto.SearchDTO;
import com.project.toy.likes.dto.LikesDTO;
import com.project.toy.paging.Pagination;
import com.project.toy.paging.PagingResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {

	private final ReviewMapper reviewMapper;
	
	public PagingResponse<ReviewResponseDTO> findAll(final SearchDTO params) {
		int count = reviewMapper.countReview(params);
		if(count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);
		
		List<ReviewResponseDTO> list = reviewMapper.findAll(params);
		
		return new PagingResponse<>(list, pagination);
	}
	
	public List<ReviewResponseDTO> findNotice(SearchDTO params) {
		return reviewMapper.findNotice(params);
	}
	
	public List<LikesDTO> findLikesBestReview(SearchDTO params) {
		return reviewMapper.findLikesBestReview(params);
	}
	
	public ReviewResponseDTO findByReviewId(Long id) {
		return reviewMapper.findByReviewId(id);
	}

	public void saveReview(ReviewRequestDTO params) {
		reviewMapper.saveReview(params);
	}
	
	public void updateReview(ReviewRequestDTO params) {
		reviewMapper.updateReview(params);
	}
	
	public void deleteReview(Long id) {
		reviewMapper.deleteByReviewId(id);
	}
	
	public void countHits(Long id) {
		reviewMapper.countHits(id);
	}

	public List<ReviewFilterDTO> reviewFilter(ReviewFilterDTO filterDTO) {
		return reviewMapper.reviewFilter(filterDTO);
	}
}
