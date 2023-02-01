package com.project.toy.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.toy.user.dto.LockUserDTO;

@Mapper
public interface UserMapper {

	public LockUserDTO findByLockUser(String userId);
	public void updateFailLogin(LockUserDTO params);

}
