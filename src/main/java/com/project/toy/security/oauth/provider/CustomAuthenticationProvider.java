package com.project.toy.security.oauth.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.toy.security.oauth.config.CustomUserDetails;
import com.project.toy.security.service.CustomUserService;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.service.UserService;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
		
	@Autowired
	private CustomUserService customUserService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String userId = (String) authentication.getPrincipal();
		String userPwd = (String) authentication.getCredentials();
		
		CustomUserDetails customUserDetails = (CustomUserDetails) customUserService.loadUserByUsername(userId);
		
		UserDTO params = new UserDTO();
		params.setUserId(userId);
		params.setUserPwd(userPwd);
		
		UserDTO userInfo = userService.selectUser(params);
		String resultUserPwd = "";
		
		// 사용자 존재여부 체크
		if(userInfo == null) {
			throw new BadCredentialsException("존재하지 않는 아이디입니다.");
		} else {
			resultUserPwd = userInfo.getUserPwd();
			
			// 비밀번호 체크
			if(!bCryptPasswordEncoder.matches(userPwd, resultUserPwd)) {
				throw new BadCredentialsException("입력하신 비밀번호가 일치하지 않습니다.");
			}
		}
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, userPwd, customUserDetails.getAuthorities());
		authToken.setDetails(customUserDetails);
		
		return authToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
