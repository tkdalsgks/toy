package com.project.toy.admin.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.toy.admin.dto.AdminDTO;
import com.project.toy.admin.service.AdminService;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UserDTO;

@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private HttpSession session;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/admin")
	public String admin(Authentication auth, Model model) {
		log.info("***** Admin Page Call *****");
		
		System.out.println(auth);
		if(auth == null) {
			return "redirect:/join";
		}
		
		SessionUser user = (SessionUser) session.getAttribute("user");
		if(user != null) {
			model.addAttribute("user", user.getUserNickname());
		}
		
		List<UserDTO> selectListUser = adminService.selectListUser();
		model.addAttribute("selectListUser", selectListUser);
		
		List<AdminDTO> selectAuthModel = adminService.selectAuthModel();
		model.addAttribute("selectAuthModel", selectAuthModel);
		
		return "admin/admin";
	}

}
