package com.project.toy.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.toy.user.dto.CertifiedUserDTO;
import com.project.toy.user.dto.LockUserDTO;
import com.project.toy.user.dto.UpdateUserDTO;
import com.project.toy.user.dto.UserDTO;

@Mapper
public interface UserMapper {

	public LockUserDTO findByLockUser(String userId);
	public void updateFailLogin(LockUserDTO params);
	public UserDTO findByUserId(String userEmail);
	public UserDTO findByUserPwd(String userId, String userEmail);
	public int updateProfile(UpdateUserDTO params);
	public void updateUserPwd(UserDTO params);
	
	
	public void certifiedEmail(CertifiedUserDTO params);
	public void successCertifiedEmail(CertifiedUserDTO params);
	public CertifiedUserDTO selectCertifiedEmail(CertifiedUserDTO params);
	public void successCertifiedRole(UserDTO userDTO);

}
