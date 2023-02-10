package com.project.toy.security.service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.toy.common.dto.LoginLogDTO;
import com.project.toy.security.mapper.SecurityMapper;
import com.project.toy.security.oauth.config.CustomUserDetails;
import com.project.toy.user.dto.LockUserDTO;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {

	@Autowired
	private SecurityMapper securityMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		UserDTO userDTO = securityMapper.findByUserId(userId);
		
		if(userDTO != null) {
			LockUserDTO lockUserDTO = userService.selectLockUser(userDTO);
			if(lockUserDTO.getLockYn().equals("N")) {
				log.info("-----");
				log.info("General: 이미 가입된 유저입니다.");
	            log.info("General: " + userDTO.getUserId());
	            log.info("-----");
	            session.setAttribute("user", new SessionUser(userDTO));
	            
	            LoginLogDTO loginLogDTO = new LoginLogDTO();
	            loginLogDTO.setLoginId(userDTO.getUserId());
	            loginLogDTO.setAccessIp(getLocalIpAddress());
	            securityMapper.insertLoginLog(loginLogDTO);
	            
	            return new CustomUserDetails(userDTO);
			} else {			
				throw new LockedException("해당 계정이 잠겼습니다.\n비밀번호 찾기를 진행하여 새로운 비밀번호로 변경하세요.");
			}
		} else {
			throw new UsernameNotFoundException("존재하지 않는 아이디입니다.");
		}
		
	}
	
	// 현재 접속한 로컬 IPv4 주소 반환
	public static String getLocalIpAddress() {
		String hostAddr = "";
		try {
			Enumeration<NetworkInterface> nienum = NetworkInterface.getNetworkInterfaces();
			while (nienum.hasMoreElements()) {
				NetworkInterface ni = nienum.nextElement();
				Enumeration<InetAddress> ia = ni.getInetAddresses();
				
				while (ia.hasMoreElements()) {
					InetAddress inetAddress = ia.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
						hostAddr = inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch(SocketException e) {
			e.printStackTrace();
		}
		return hostAddr;
	}
}
