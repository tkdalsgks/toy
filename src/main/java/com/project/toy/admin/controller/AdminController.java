package com.project.toy.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.project.toy.admin.dto.AdminDTO;
import com.project.toy.admin.service.AdminService;
import com.project.toy.board.dto.BoardResponseDTO;
import com.project.toy.comment.dto.CommentResponseDTO;
import com.project.toy.common.dto.SearchDTO;
import com.project.toy.paging.PagingResponse;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UpdateUserDTO;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
	
	private final AdminService adminService;
	private final UserService userService;
	
	private final HttpSession session;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("")
	public String admin(@ModelAttribute("params") final SearchDTO params, Authentication auth, Model model) {
		log.info("##### Admin Page List __ Call #####");
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		
		if(auth != null) {
			PagingResponse<UserDTO> userList = adminService.selectListUser(params);
			List<AdminDTO> authList = adminService.selectAuthModel();

			model.addAttribute("user", user.getUserNickname());
			model.addAttribute("userList", userList);
			model.addAttribute("authList", authList);
			
			String rawPwd = "user01@#";
        	String encPwd = bCryptPasswordEncoder.encode(rawPwd);
			log.info("PASSWORD : {}", encPwd);
		}
		return "admin/admin";
	}
	
	@Transactional
	@GetMapping("/{userId}/activity")
	public String detail(@PathVariable(value = "userId", required = false) String userId, AdminDTO params, 
			HttpServletRequest request, HttpServletResponse response, Authentication auth, Model model) {
		log.info("##### Admin Page Activity __ Call" + userId + " #####");
		
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
		
		return "admin/detail";
	}
	
	@PostMapping("/save")
	public @ResponseBody JsonObject save(@RequestBody UpdateUserDTO updateUserDTO, Model model) {
		log.info("##### Admin Page Save __ API #####");
		
		JsonObject jsonObj = new JsonObject();
		
		adminService.updateAuthUser(updateUserDTO);
		
		return jsonObj;
	}
}
