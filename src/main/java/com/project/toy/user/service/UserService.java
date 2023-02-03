package com.project.toy.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.toy.security.mapper.SecurityMapper;
import com.project.toy.user.dto.LockUserDTO;
import com.project.toy.user.dto.Role;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SecurityMapper securityMapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public UserDTO saveUser(UserDTO userDTO) {
        if(securityMapper.findByUserId(userDTO.getUserId()) != null){
            log.info("General: 이미 가입된 유저입니다.");
            log.info("General: " + userDTO.getUserId());
            userDTO = securityMapper.findByUserId(userDTO.getUserId());
        } else {
        	String rawPwd = userDTO.getUserPwd();
        	String encPwd = bCryptPasswordEncoder.encode(rawPwd);
        	userDTO.setUserPwd(encPwd);
        	userDTO.setProvider("general");
        	userDTO.setRole(Role.USER);
        	securityMapper.saveUser(userDTO);
            
            log.info("General: 정상적으로 가입 완료되었습니다.");
            log.info("General: " + userDTO.getUserId());
            userDTO = securityMapper.findByUserId(userDTO.getUserId());
        }

        return userDTO;
	}
	
	public UserDTO selectUser(UserDTO params) {
		return securityMapper.findByUserId(params.getUserId());
	}
	
	public LockUserDTO selectLockUser(UserDTO params) {
		return userMapper.findByLockUser(params.getUserId());
	}

	public void updateFailLogin(LockUserDTO params) {
		userMapper.updateFailLogin(params);
	}
	
	public UserDTO findByUserId(String userEmail) {
		return userMapper.findByUserId(userEmail);
	}
	
	public UserDTO findByUserPwd(String userId, String userEmail) {
		return userMapper.findByUserPwd(userId, userEmail);
	}	
}
