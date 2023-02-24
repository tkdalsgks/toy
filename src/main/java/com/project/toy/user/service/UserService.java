package com.project.toy.user.service;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.toy.security.mapper.SecurityMapper;
import com.project.toy.user.dto.LockUserDTO;
import com.project.toy.user.dto.Role;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UpdateUserDTO;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserMapper userMapper;
	private final SecurityMapper securityMapper;
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final HttpSession session;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

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
        	userDTO.setRole(Role.GUEST);
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
	
	@Transactional
	public boolean updateProfile(UpdateUserDTO params) {
		int queryResult = 0;
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userMapper.findByUserId(sessionUser.getUserEmail());
		
		if(user.getUserId() != null) {
			queryResult = userMapper.updateProfile(params);
		}

		return (queryResult == 1) ? true : false;
	}
}
