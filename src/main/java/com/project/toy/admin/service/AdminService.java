package com.project.toy.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.toy.admin.dto.AdminDTO;
import com.project.toy.admin.mapper.AdminMapper;
import com.project.toy.user.dto.UserDTO;

@Service
public class AdminService {
	
	@Autowired
	private AdminMapper adminMapper;
	
	public List<UserDTO> selectListUser() {
		return adminMapper.selectListUser();
	}

	public List<AdminDTO> selectAuthModel() {
		return adminMapper.selectAuthModel();
	}

}
