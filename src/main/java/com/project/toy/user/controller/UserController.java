package com.project.toy.user.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.toy.user.dto.SessionUser;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final HttpSession session;

	@GetMapping({"/settings", "/settings/profile"})
	public String profile(Authentication auth, Model model) {
		SessionUser user = (SessionUser) session.getAttribute("user");
		if(auth != null) {
			model.addAttribute("nickname", user.getUserNickname());
		}
		
		return "user/profile";
	}
	
	@GetMapping("/settings/account")
	public String account() {
		
		return "user/account";
	}
}
