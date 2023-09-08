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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.gson.JsonObject;
import com.project.toy.admin.dto.AdminDTO;
import com.project.toy.admin.service.AdminService;
import com.project.toy.board.dto.BoardResponseDTO;
import com.project.toy.comment.dto.CommentResponseDTO;
import com.project.toy.email.service.EmailService;
import com.project.toy.points.dto.PointsResponseDTO;
import com.project.toy.points.service.PointsService;
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
	private final PointsService pointsService;
	
	private final HttpSession session;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/{userId}/profile")
	public String profile(@PathVariable(value = "userId", required = false) String userId, Authentication auth, Model model) {
		log.info("##### User Profile Page __ Call {} #####", userId);
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		if(auth != null) {
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("userNickname", user.getUserNickname());
			model.addAttribute("userEmail", user.getUserEmail());
			model.addAttribute("provider", user.getProvider());
			model.addAttribute("role", user.getRole());
		}
		
		if(user.getProfileImg() == null) {
			model.addAttribute("profileImg", null);
		} else {
			model.addAttribute("profileImg", user.getProfileImg());
		}
		
		return "user/profile";
	}
	
	@GetMapping("/{userId}/account")
	public String account(@PathVariable(value = "userId", required = false) String userId, Authentication auth, Model model) {
		log.info("##### User Account Page __ Call {} #####", userId);
		
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
	@PutMapping("/{userId}/profile")
	public JsonObject saveProfile(@PathVariable(value = "userId", required = false) String userId, 
			@RequestPart(value = "profileImg", required = false) MultipartFile profileImg, 
			@RequestBody final UpdateUserDTO params) {
		log.info("##### User Profile __ API {} #####", userId);
		
		JsonObject jsonObj = new JsonObject();
		
		try {
			SessionUser sessionUser = (SessionUser) session.getAttribute("user");
			UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
			
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
	@PostMapping("/{userId}/account")
	public JsonObject certifiedAccount(@PathVariable(value = "userId", required = false) String userId, 
			@RequestParam("userEmail") String userEmail, @RequestBody UserDTO params, Model model) {
		log.info("##### User Account __ API {} #####", userId);
		
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
	@GetMapping("/{userId}/activity")
	public String detail(@PathVariable(value = "userId", required = false) String userId, AdminDTO params, 
			HttpServletRequest request, HttpServletResponse response, Authentication auth, Model model) {
		log.info("##### User Activity Page __ Call {} #####", userId);
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		model.addAttribute("userId", user.getUserId());
		model.addAttribute("role", user.getRole());
		
		// 게시글 리스트
		AdminDTO admin = adminService.findByUserId(userId);
		List<BoardResponseDTO> boardCommunity = adminService.findByBoardIdAndCommunity(userId);
		List<BoardResponseDTO> boardReview = adminService.findByBoardIdAndReview(userId);
		model.addAttribute("admin", admin);
		model.addAttribute("boardCommunity", boardCommunity);
		model.addAttribute("boardReview", boardReview);
		
		// 댓글 리스트
		List<CommentResponseDTO> boardComment = adminService.findByComment(userId);
		model.addAttribute("boardComment", boardComment);
		
		// 권한 모델
		AdminDTO userModel = adminService.selectAuthUser(userId);
		List<AdminDTO> authList = adminService.selectAuthModel();
		model.addAttribute("userModel", userModel.getModelName());
		model.addAttribute("authList", authList);
		
		// 소멸예정 활동점수
		PointsResponseDTO expirePoints = pointsService.expirePoints(userId);
		model.addAttribute("expirePoints", expirePoints);
		
		// 포인트 적립내역
		List<PointsResponseDTO> earningsPoints = pointsService.earningsPoints(userId);
		model.addAttribute("earningsPoints", earningsPoints);
		
		return "user/detail";
	}
	
	@PostMapping("/upload/profileImg")
	public void profileImageUrlUpdate(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest upload) throws Exception {
		
		userService.upload(request, response, upload);
	}
}
