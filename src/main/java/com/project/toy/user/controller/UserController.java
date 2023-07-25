package com.project.toy.user.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.project.toy.admin.dto.AdminDTO;
import com.project.toy.admin.service.AdminService;
import com.project.toy.board.dto.BoardResponseDTO;
import com.project.toy.comment.dto.CommentResponseDTO;
import com.project.toy.email.service.EmailService;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UpdateUserDTO;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	private final EmailService emailService;
	private final AdminService adminService;
	
	private final HttpSession session;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@GetMapping("{userId}/profile")
	public String profile(@PathVariable(value = "userId", required = false) String userId, Authentication auth, Model model) {
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		if(auth != null) {
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("userNickname", user.getUserNickname());
			model.addAttribute("userEmail", user.getUserEmail());
			model.addAttribute("provider", user.getProvider());
		}
		
		return "user/profile";
	}
	
	@GetMapping("{userId}/account")
	public String account(@PathVariable(value = "userId", required = false) String userId, Authentication auth, Model model) {
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		if(auth != null) {
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("userNickname", user.getUserNickname());
			model.addAttribute("userEmail", user.getUserEmail());
			model.addAttribute("provider", user.getProvider());
			model.addAttribute("role", user.getRole());
		}
		
		return "user/account";
	}
	
	@ResponseBody
	@PutMapping("{userId}/profile")
	public JsonObject saveProfile(@PathVariable(value = "userId", required = false) String userId, @RequestBody final UpdateUserDTO params, Model model) {
		JsonObject jsonObj = new JsonObject();
		
		try {
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
			model.addAttribute("role", user.getRole());
			
			LocalDateTime pwdUDate = user.getPwdUDate();
			LocalDateTime now = LocalDateTime.now();
			long day = ChronoUnit.DAYS.between(pwdUDate, now);
			
			if(day > 0) {
				boolean result = userService.updateProfile(params);
				jsonObj.addProperty("result", result);
			} else {
				jsonObj.addProperty("result", "exceedCount");
				jsonObj.addProperty("message", "변경 횟수를 이미 소모하였습니다.\n내일 다시 해주세요.");
			}
		} catch(DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");
		} catch(Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}
		return jsonObj;
	}
	
	@ResponseBody
	@PostMapping("{userId}/account")
	public JsonObject certifiedAccount(@PathVariable(value = "userId", required = false) String userId, 
			@RequestParam("userEmail") String userEmail, @RequestBody UserDTO params, Model model) {
		JsonObject jsonObj = new JsonObject();
		
		try {
			emailService.certifiedEmail(params, userEmail);
		} catch(DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");
		} catch(Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}
		return jsonObj;
	}
	
	@Transactional
	@GetMapping("/member/detail")
	public String detail(@RequestParam Long id, AdminDTO params, 
			HttpServletRequest request, HttpServletResponse response, Authentication auth, Model model) {
		log.info("# Admin User Page Detail?id = " + id);
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		model.addAttribute("role", user.getRole());
		
		// 게시글 리스트
		AdminDTO admin = adminService.findByUserId(id);
		List<BoardResponseDTO> boardCommunity = adminService.findByBoardIdAndCommunity(id);
		List<BoardResponseDTO> boardReview = adminService.findByBoardIdAndReview(id);
		model.addAttribute("admin", admin);
		model.addAttribute("boardCommunity", boardCommunity);
		model.addAttribute("boardReview", boardReview);
		
		// 댓글 리스트
		List<CommentResponseDTO> boardComment = adminService.findByComment(id);
		model.addAttribute("boardComment", boardComment);
		
		// 권한 모델
		AdminDTO userModel = adminService.selectAuthUser(id);
		List<AdminDTO> authList = adminService.selectAuthModel();
		model.addAttribute("userModel", userModel.getModelName());
		model.addAttribute("authList", authList);
		
		return "user/detail";
	}
}
