package com.project.toy.security.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "index", description = "로그인 및 회원가입 API")
@Controller
@RequiredArgsConstructor
public class SecurityController {
	
	private final UserService userService;
	private final SecurityMapper securityMapper;
	private final ChatRoomService chatRoomService;
	
	private final HttpSession session;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Operation(summary = "메인 홈", description = "메인 홈 조회")
	@GetMapping({"", "/"})
	public String index(Authentication auth, Model model) {
		SessionUser user = (SessionUser) session.getAttribute("user");
		if(auth != null) {
			model.addAttribute("userId", user.getUserId());
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
	@Operation(summary = "회원가입 페이지", description = "회원가입 페이지 조회")
	@GetMapping("/join")
	public String join() {
		log.info("***** Join Page Call *****");
		
		return "user/join";
	}
	
	/**
	 * 일반 회원가입
	 * @param userDTO
	 */
	@Operation(summary = "회원가입", description = "일반 사용자 회원가입 메서드")
	@PostMapping("/join")
	public String saveUser(UserDTO userDTO, String userId, Model model) {
		MessageDTO message;
		
		if(securityMapper.findByUserId(userId) != null) {
			message = new MessageDTO("회원가입 오류 !\n회원가입을 처음부터 다시 진행해주세요.", "/join", RequestMethod.POST, null);
		} else {
			userService.saveUser(userDTO);
			message = new MessageDTO("회원가입이 완료되었습니다.\n마이페이지에서 추가 인증하면 OYEZ의 모든 콘텐츠를 이용하실 수 있습니다.", "/", RequestMethod.POST, null);
		}
		return showMessageAndRedirect(message, model);
	}
	
	@Operation(summary = "아이디 중복확인", description = "아이디 중복확인 메서드")
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
	
	@Operation(summary = "닉네임 중복확인", description = "닉네임 중복확인 메서드")
	@ResponseBody
	@PostMapping("/check/nickname")
	public Map<String, String> checkNickname(String userNickname) {
		Map<String, String> map = new HashMap<String, String>();
		
		if(securityMapper.findByUserNickname(userNickname) == null) {
			map.put("result", "true");
		} else {
			map.put("result", "false");
			map.put("userNickname", userNickname);
		}
		
		return map;
	}
	
	@Operation(summary = "이메일 중복확인", description = "이메일 중복확인 메서드")
	@ResponseBody
	@PostMapping("/check/email")
	public Map<String, String> checkEmail(String userEmail) {
		Map<String, String> map = new HashMap<String, String>();
		
		if(securityMapper.findByUserEmail(userEmail) == null) {
			map.put("result", "true");
		} else {
			map.put("result", "false");
			map.put("userEmail", userEmail);
		}
		
		return map;
	}
	
	@Operation(summary = "아이디 찾기", description = "아이디 찾기 메서드")
	@ResponseBody
	@PostMapping("/find/id")
	public Map<String, String> findId(String userEmail) {
		Map<String, String> map = new HashMap<String, String>();
		
		UserDTO userDTO = userService.findByUserId(userEmail);
		if(userDTO == null) {
			map.put("result", "false");
		} else {
			String userId = userDTO.getUserId();
			map.put("findId", userId);
			map.put("result", "true");
		}
		
		return map;
	}
	
	@Operation(summary = "비밀번호 찾기", description = "비밀번호 찾기 메서드")
	@ResponseBody
	@PostMapping("/find/password")
	public Map<String, String> findPassword(String userId, String userEmail) {
		Map<String, String> map = new HashMap<String, String>();
		
		UserDTO userDTO = userService.findByUserPwd(userId, userEmail);
		if(userDTO == null) {
			map.put("result", "false");
		} else {
			String userPwd = userDTO.getUserPwd();
			map.put("findPwd", userPwd);
			map.put("result", "true");
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
