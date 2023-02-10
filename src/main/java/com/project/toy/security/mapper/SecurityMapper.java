package com.project.toy.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.project.toy.common.dto.LoginLogDTO;
import com.project.toy.user.dto.UserDTO;

@Mapper
@Repository
public interface SecurityMapper {

	/**
	 * 회원 조회
	 * @param userId
	 * @return
	 */
	public UserDTO findByUserId(String userId);
	public UserDTO findByUserEmail(String userEmail);
	public UserDTO findByUserNickname(String userNickname);
	
	/**
	 * 회원 가입
	 * @param userDTO
	 * @return 
	 */
	public void saveUser(UserDTO userDTO);
	
	public void insertLoginLog(LoginLogDTO loginLogDTO);
}
