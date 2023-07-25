package com.project.toy.admin.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.toy.admin.dto.AdminDTO;
import com.project.toy.admin.mapper.AdminMapper;
import com.project.toy.board.dto.BoardResponseDTO;
import com.project.toy.comment.dto.CommentResponseDTO;
import com.project.toy.common.dto.SearchDTO;
import com.project.toy.paging.Pagination;
import com.project.toy.paging.PagingResponse;
import com.project.toy.user.dto.UpdateUserDTO;
import com.project.toy.user.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	
	private final AdminMapper adminMapper;
	
	public List<AdminDTO> selectAuthModel() {
		return adminMapper.selectAuthModel();
	}
	
	public AdminDTO selectAuthUser(String userId) {
		return adminMapper.selectAuthUser(userId);
	}
	
	public PagingResponse<UserDTO> selectListUser(final SearchDTO params) {
		int count = adminMapper.countUser(params);
		if(count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);
		
		List<UserDTO> list = adminMapper.selectListUser();
		
		return new PagingResponse<>(list, pagination);
	}

	public AdminDTO findByUserId(String userId) {
		return adminMapper.findByUserId(userId);
	}
	
	public List<BoardResponseDTO> findByBoardIdAndCommunity(String userId) {
		return adminMapper.findByBoardIdAndCommunity(userId);
	}
	
	public List<BoardResponseDTO> findByBoardIdAndReview(String userId) {
		return adminMapper.findByBoardIdAndReview(userId);
	}
	
	public List<CommentResponseDTO> findByComment(String userId) {
		return adminMapper.findByComment(userId);
	}

	public void updateAuthUser(UpdateUserDTO updateUserDTO) {
		adminMapper.updateAuthUser(updateUserDTO);
	}

}
