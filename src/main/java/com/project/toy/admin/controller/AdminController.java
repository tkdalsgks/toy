package com.project.toy.admin.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.toy.admin.dto.AdminDTO;
import com.project.toy.admin.service.AdminService;
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
	public String admin(Authentication auth, Model model) {
		log.info("***** Admin Page Call *****");
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		
		if(auth != null) {
			List<UserDTO> selectListUser = adminService.selectListUser();
			List<AdminDTO> selectAuthModel = adminService.selectAuthModel();

			model.addAttribute("user", user.getUserNickname());
			model.addAttribute("selectListUser", selectListUser);
			model.addAttribute("selectAuthModel", selectAuthModel);
			
		}
		return "admin/admin";
	}
}
