package com.project.toy.user.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
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
	
	private final HttpSession session;

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
	public JsonObject saveProfile(@PathVariable(value = "userId", required = false) String userId, @RequestBody final UpdateUserDTO params) {
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
}
