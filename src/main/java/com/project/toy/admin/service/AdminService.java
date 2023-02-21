package com.project.toy.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.toy.admin.dto.AdminDTO;
import com.project.toy.admin.mapper.AdminMapper;
import com.project.toy.user.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
	
	private final AdminMapper adminMapper;
	
	public List<UserDTO> selectListUser() {
		return adminMapper.selectListUser();
	}

	public List<AdminDTO> selectAuthModel() {
		return adminMapper.selectAuthModel();
	}

}
