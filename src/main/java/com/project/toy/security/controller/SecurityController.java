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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.toy.chat.service.ChatRoomService;
import com.project.toy.common.dto.MessageDTO;
import com.project.toy.security.mapper.SecurityMapper;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.service.UserService;

@Controller
public class SecurityController {
	
	@Autowired
	private SecurityMapper securityMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChatRoomService chatRoomService;
	
	@Autowired
	private HttpSession session;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping({"", "/"})
	public String index(Authentication auth, Model model) {
		SessionUser user = (SessionUser) session.getAttribute("user");
		if(auth != null) {
			model.addAttribute("user", user.getUserNickname());
			model.addAttribute("list", chatRoomService.findAllRooms());
			return "main";
		} else {
			return "index";
		}
	}
	
	/**
	 * 회원가입 페이지
	 * @return
	 */
	@GetMapping("/join")
	public String join() {
		log.info("***** Join Page Call *****");
		
		return "user/join";
	}
	
	/**
	 * 일반 회원가입
	 * @param userDTO
	 */
	@PostMapping("/join")
	public String saveUser(UserDTO userDTO, String userId, Model model) {
		MessageDTO message;
		
		if(securityMapper.findByUserId(userId) != null) {
			message = new MessageDTO("회원가입 오류 !\n회원가입을 처음부터 다시 진행해주세요.", "/join", RequestMethod.POST, null);
		} else {
			userService.saveUser(userDTO);
			message = new MessageDTO("회원가입이 완료되었습니다.", "/", RequestMethod.POST, null);
		}
		return showMessageAndRedirect(message, model);
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
	
	/**
	 * 성공/실패 메세지를 띄우고 해당 주소 리다이렉트
	 * @param message
	 * @param model
	 * @return
	 */
	private String showMessageAndRedirect(final MessageDTO message, Model model) {
		model.addAttribute("message", message);
		
		return "common/messageRedirect";
	}
}
