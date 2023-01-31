package com.project.toy.security.service;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.toy.security.mapper.SecurityMapper;
import com.project.toy.security.oauth.config.CustomUserDetails;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {

	@Autowired
	private SecurityMapper securityMapper;
	
	@Autowired
	private HttpSession session;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		UserDTO userDTO = securityMapper.findByUserId(userId);
		if(userDTO != null) {
			log.info("-----");
			log.info("General: 이미 가입된 유저입니다.");
            log.info("General: " + userDTO.getUserId());
            log.info("-----");
            session.setAttribute("user", new SessionUser(userDTO));
            
            return new CustomUserDetails(userDTO);
		} else {
			throw new UsernameNotFoundException(userId + "는 존재하지 않는 아이디입니다.");
		}
	}
}
