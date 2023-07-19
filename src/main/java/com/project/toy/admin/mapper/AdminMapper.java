package com.project.toy.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.toy.admin.dto.AdminDTO;
import com.project.toy.board.dto.BoardResponseDTO;
import com.project.toy.common.dto.SearchDTO;
import com.project.toy.user.dto.UpdateUserDTO;
import com.project.toy.user.dto.UserDTO;

@Mapper
public interface AdminMapper {

	public List<AdminDTO> selectAuthModel();
	public AdminDTO selectAuthUser(Long id);
	public List<UserDTO> selectListUser();
	public AdminDTO findByUserId(Long id);
	public List<BoardResponseDTO> findByBoardIdAndCommunity(Long id);
	public List<BoardResponseDTO> findByBoardIdAndReview(Long id);
	public int countUser(SearchDTO params);
	public void updateAuthUser(UpdateUserDTO updateUserDTO);
}
