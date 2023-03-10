package com.project.toy.security.oauth.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.toy.security.oauth.config.CustomUserDetails;
import com.project.toy.security.service.CustomUserService;
import com.project.toy.user.dto.LockUserDTO;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
		
	private final CustomUserService customUserService;
	private final UserService userService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
			throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
		} else {
			resultUserPwd = userInfo.getUserPwd();
			LockUserDTO lockUserDTO = userService.selectLockUser(params);
			
			// 비밀번호 체크
			if(!bCryptPasswordEncoder.matches(userPwd, resultUserPwd)) {
				int failCount = lockUserDTO.getFailCnt() + 1;
				lockUserDTO.setFailCnt(failCount);
				lockUserDTO.setLockYn(failCount >= 5 ? "Y" : "N");
				userService.updateFailLogin(lockUserDTO);
				
				throw new BadCredentialsException("비밀번호를 " + failCount + "회 틀렸습니다." + "\n5회 이상 틀릴 시 계정이 잠깁니다.\n");
			} else {
				lockUserDTO.setFailCnt(0);
				userService.updateFailLogin(lockUserDTO);
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
