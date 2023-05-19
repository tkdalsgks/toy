package com.project.toy.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.toy.admin.dto.AdminDTO;
import com.project.toy.board.dto.BoardResponseDTO;
import com.project.toy.common.dto.SearchDTO;
import com.project.toy.user.dto.UserDTO;

@Mapper
public interface AdminMapper {

	public List<AdminDTO> selectAuthModel();
	public List<UserDTO> selectListUser();
	public AdminDTO findByUserId(Long id);
	public List<BoardResponseDTO> findByBoardId(Long id);
	public int countUser(SearchDTO params);
}
