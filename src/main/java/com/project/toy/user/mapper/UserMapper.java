package com.project.toy.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.toy.user.dto.LockUserDTO;
import com.project.toy.user.dto.UserDTO;

@Mapper
public interface UserMapper {

	public LockUserDTO findByLockUser(String userId);
	public void updateFailLogin(LockUserDTO params);
	public UserDTO findByUserId(String userEmail);
	public UserDTO findByUserPwd(String userId, String userEmail);

}
