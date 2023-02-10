package com.project.toy.security.service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.project.toy.common.dto.LoginLogDTO;
import com.project.toy.security.mapper.SecurityMapper;
import com.project.toy.security.oauth.config.OAuthAttributes;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	@Autowired
	private SecurityMapper securityMapper;
	
	@Autowired
	private HttpSession session;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		
		// OAuth2 서비스 ID
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		// OAuth2 로그인 진행 시 키가 되는 필드 값
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		
		// OAuth2UserService
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
		UserDTO userDTO = saveOrUpdate(attributes);
		session.setAttribute("user", new SessionUser(userDTO));
		
		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(userDTO.getRoleKey())),
				attributes.getAttributes(),
				attributes.getNameAttributeKey());
	}
	
	// 유저 생성 및 수정 서비스 로직
	private UserDTO saveOrUpdate(OAuthAttributes attributes) {
		UserDTO userDTO;
        
        if(securityMapper.findByUserEmail(attributes.getUserEmail()) != null){
        	log.info("-----");
        	log.info(attributes.getProvider() + ": 이미 가입된 유저입니다.");
            log.info(attributes.getProvider() + ": " + attributes.getUserId());
            log.info("-----");
            userDTO = securityMapper.findByUserEmail(attributes.getUserEmail());
            
            LoginLogDTO loginLogDTO = new LoginLogDTO();
            loginLogDTO.setLoginId(userDTO.getUserId());
            loginLogDTO.setAccessIp(getLocalIpAddress());
            securityMapper.insertLoginLog(loginLogDTO);
        } else {
        	userDTO = attributes.Entity();
        	
        	SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        	Date date = new Date();
        	String today = sdf.format(date);
        	userDTO.setUserNickname(userDTO.getUserNickname() + "_" + today + (int)(Math.random() * 100000 + 1));
        	securityMapper.saveUser(userDTO);
            
        	log.info("-----");
        	log.info(attributes.getProvider() + ": 정상적으로 가입 완료되었습니다.");
        	log.info(attributes.getProvider() + ": " + attributes.getUserId());
        	log.info("-----");
            userDTO = securityMapper.findByUserEmail(attributes.getUserEmail());
        }

        return userDTO;
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
