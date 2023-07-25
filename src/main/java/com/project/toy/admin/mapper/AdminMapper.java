package com.project.toy.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.toy.admin.dto.AdminDTO;
import com.project.toy.board.dto.BoardResponseDTO;
import com.project.toy.comment.dto.CommentResponseDTO;
import com.project.toy.common.dto.SearchDTO;
import com.project.toy.user.dto.UpdateUserDTO;
import com.project.toy.user.dto.UserDTO;

@Mapper
public interface AdminMapper {

	public List<AdminDTO> selectAuthModel();
	public AdminDTO selectAuthUser(String userId);
	public List<UserDTO> selectListUser();
	public AdminDTO findByUserId(String userId);
	public List<BoardResponseDTO> findByBoardIdAndCommunity(String userId);
	public List<BoardResponseDTO> findByBoardIdAndReview(String userId);
	public List<CommentResponseDTO> findByComment(String userId);
	public int countUser(SearchDTO params);
	public void updateAuthUser(UpdateUserDTO updateUserDTO);
}
