package com.project.toy.admin.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.toy.admin.dto.AdminDTO;
import com.project.toy.admin.service.AdminService;
import com.project.toy.board.dto.BoardResponseDTO;
import com.project.toy.common.dto.SearchDTO;
import com.project.toy.paging.PagingResponse;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {
	
	private final UserService userService;
	private final AdminService adminService;
	private final HttpSession session;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/admin")
	public String admin(@ModelAttribute("params") final SearchDTO params, Authentication auth, Model model) {
		log.info("***** Admin Page Call *****");
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		
		if(auth != null) {
			PagingResponse<UserDTO> userList = adminService.selectListUser(params);
			List<AdminDTO> authList = adminService.selectAuthModel();

			model.addAttribute("user", user.getUserNickname());
			model.addAttribute("userList", userList);
			model.addAttribute("authList", authList);
			
		}
		return "admin/admin";
	}
	
	@Transactional
	@GetMapping("/admin/detail")
	public String detail(@RequestParam Long id, AdminDTO params, 
			HttpServletRequest request, HttpServletResponse response, Authentication auth, Model model) {
		log.info("# Admin User Page Detail?id = " + id);
		
		AdminDTO admin = adminService.findByUserId(id);
		List<BoardResponseDTO> board = adminService.findByBoardId(id);
		
		model.addAttribute("admin", admin);
		model.addAttribute("board", board);
		
		return "admin/detail";
	}
}
