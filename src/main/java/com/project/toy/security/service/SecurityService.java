package com.project.toy.security.service;

import org.springframework.stereotype.Service;

import com.project.toy.security.mapper.SecurityMapper;
import com.project.toy.user.dto.CertifiedUserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityService {
	
	private final SecurityMapper securityMapper;
	
	public CertifiedUserDTO selectCertifiedUser(String userId) {
		return securityMapper.selectCertifiedUser(userId);
	}

	public Object findByUserId(String userId) {
		return securityMapper.findByUserId(userId);
	}

	public Object findByUserNickname(String userNickname) {
		return securityMapper.findByUserNickname(userNickname);
	}

	public Object findByUserEmail(String userEmail) {
		return securityMapper.findByUserEmail(userEmail);
	}
	
	
}
