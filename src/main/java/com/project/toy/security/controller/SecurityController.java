package com.project.toy.security.controller;

import java.util.HashMap;
import java.util.List;
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

import com.project.toy.chat.dto.ChatRoomDTO;
import com.project.toy.chat.service.ChatRoomService;
import com.project.toy.common.dto.MessageDTO;
import com.project.toy.email.service.EmailService;
import com.project.toy.security.service.SecurityService;
import com.project.toy.user.dto.CertifiedUserDTO;
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
	private final SecurityService securityService;
	private final ChatRoomService chatRoomService;
	private final EmailService emailService;
	
	private final HttpSession session;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Operation(summary = "메인 홈", description = "메인 홈 조회")
	@GetMapping({"", "/"})
	public String index(Authentication auth, Model model, String roomId) {
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		if(auth != null) {
			UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
			
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("user", user.getUserNickname());
			model.addAttribute("userEmail", user.getUserEmail());
			model.addAttribute("role", user.getRole());
			
			// 계정 인증 여부
			CertifiedUserDTO certified = securityService.selectCertifiedUser(user.getUserId());
			if(certified != null) {
				model.addAttribute("certified", certified.getUserId());				
			}
			
			log.info("##### Main Page __ Call {} #####", user.getUserId());
			
			//System.out.println("홈 채팅방 조회 : " + chatRoomService.findAllRooms().toString());
			// 채팅방 조회
			List<ChatRoomDTO> roomList = chatRoomService.findAllRooms();
			ChatRoomDTO room = chatRoomService.findRoomById(roomId);
			model.addAttribute("roomList", roomList);
			model.addAttribute("room", room);
			
			if(user.getProfileImg() == null) {
				model.addAttribute("profileImg", null);
			} else {
				model.addAttribute("profileImg", user.getProfileImg());
			}
			
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
		log.info("##### Join Page __ Call #####");
		
		return "user/join";
	}
	
	/**
	 * 일반 회원가입
	 * @param userDTO
	 */
	@Operation(summary = "회원가입", description = "일반 사용자 회원가입 메서드")
	@PostMapping("/join")
	public String saveUser(UserDTO userDTO, String userId, Model model) {
		log.info("Join Page __ API #####");
		
		MessageDTO message;
		
		if(securityService.findByUserId(userId) != null) {
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
		
		if(securityService.findByUserId(userId) == null) {
			log.info("##### Join USER_ID {} #####", userId);
			
			map.put("result", "true");
		} else {
			log.info("##### Duplicate USER_ID {} #####", userId);
			
			map.put("result", "false");
		}
		
		return map;
	}
	
	@Operation(summary = "닉네임 중복확인", description = "닉네임 중복확인 메서드")
	@ResponseBody
	@PostMapping("/check/nickname")
	public Map<String, String> checkNickname(String userNickname) {
		Map<String, String> map = new HashMap<String, String>();
		
		if(securityService.findByUserNickname(userNickname) == null) {
			log.info("##### Join USER_ID {} #####", userNickname);
			
			map.put("result", "true");
		} else {
			log.info("##### Duplicate USER_ID {} #####", userNickname);
			
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
		
		if(securityService.findByUserEmail(userEmail) == null) {
			log.info("##### Join USER_ID {} #####", userEmail);
			
			map.put("result", "true");
		} else {
			log.info("##### Duplicate USER_ID {} #####", userEmail);
			
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
			log.info("##### Not Found USER_ID {} #####", userDTO.getUserId());
			
			map.put("result", "false");
		} else {
			log.info("##### Find USER_ID {} #####", userDTO.getUserId());
			
			String userId = userDTO.getUserId();
			map.put("findId", userId);
			map.put("result", "true");
		}
		
		return map;
	}
	
	@Operation(summary = "비밀번호 찾기", description = "비밀번호 찾기 메서드")
	@ResponseBody
	@PostMapping("/find/password")
	public Map<String, String> findPassword(String userId, String userEmail) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		
		UserDTO userDTO = userService.findByUserPwd(userId, userEmail);
		if(userDTO == null) {
			log.info("##### Not Found USER_ID {} #####", userDTO.getUserId());
			
			map.put("result", "false");
		} else {
			log.info("##### Find USER_ID {} #####", userDTO.getUserId());
			
			emailService.updateUserPwd(userDTO, userEmail);
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
