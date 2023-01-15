package com.project.toy.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.toy.admin.dto.AdminDTO;
import com.project.toy.user.dto.UserDTO;

@Mapper
public interface AdminMapper {

	public List<AdminDTO> selectAuthModel();
	public List<UserDTO> selectListUser();
}
