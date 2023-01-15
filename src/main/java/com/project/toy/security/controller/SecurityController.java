package com.project.toy.security.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.toy.security.mapper.SecurityMapper;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.service.UserService;

@Controller
public class SecurityController {
	
	@Autowired
	private SecurityMapper securityMapper;
	
	@Autowired UserService userService;
	
	@Autowired
	private HttpSession session;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 메인 홈
	 * @param auth
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String login(Authentication auth, Model model) {
		log.info("***** Main Page Call *****");
		
		SessionUser user = (SessionUser) session.getAttribute("user");
		if(user != null) {
			model.addAttribute("user", user.getUserNickname());
		}
		
		return "index";		
	}
	
	/**
	 * 소셜 로그인 및 회원가입, 일반 로그인
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		log.info("***** User Login Call *****");
		
		return "user/login";
	}
	
	/**
	 * 회원가입 페이지
	 * @return
	 */
	@GetMapping("/join")
	public String join() {
		log.info("***** User Page Call *****");
		
		return "user/join";
	}
	
	/**
	 * 일반 회원가입
	 * @param userDTO
	 */
	@PostMapping("/join")
	public String saveUser(UserDTO userDTO) {
		
		try {
			userService.saveUser(userDTO);
			
			return "redirect:/";
		} catch(Exception e) {
			throw new RuntimeException();
		}
	}
	
	@ResponseBody
	@PostMapping("/check/id")
	public Map<String, String> checkId(String userId) {
		Map<String, String> map = new HashMap<String, String>();
		
		if(securityMapper.findByUserId(userId) == null) {
			map.put("result", "true");
		} else {
			map.put("result", "false");
		}
		
		return map;
	}
	
	@ResponseBody
	@PostMapping("/check/nickname")
	public Map<String, String> checkNickname(String userNickname) {
		Map<String, String> map = new HashMap<String, String>();
		
		if(securityMapper.findByUserNickname(userNickname) == null) {
			map.put("result", "true");
		} else {
			map.put("result", "false");
		}
		
		return map;
	}
	
	@ResponseBody
	@PostMapping("/check/email")
	public Map<String, String> checkEmail(String userEmail) {
		Map<String, String> map = new HashMap<String, String>();
		
		if(securityMapper.findByUserEmail(userEmail) == null) {
			map.put("result", "true");
		} else {
			map.put("result", "false");
		}
		
		return map;
	}
	
	

}
