package com.project.toy.gpt.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.toy.gpt.dto.GptClient;
import com.project.toy.security.service.SecurityService;
import com.project.toy.user.dto.CertifiedUserDTO;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class GptController {
	
    private final GptClient gptClient;
    private final UserService userService;
    private final SecurityService securityService;
    
    private final HttpSession session;
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @GetMapping("/gpt")
    public String gpt(Authentication auth, Model model) {
    	log.info("##### ChatGPT Page __ Call #####");
    	
    	SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		if(auth != null) {
			model.addAttribute("user", user.getUserNickname());
			model.addAttribute("userEmail", user.getUserEmail());
			model.addAttribute("role", user.getRole());
			
			// 계정 인증 여부
			CertifiedUserDTO certified = securityService.selectCertifiedUser(user.getUserId());
			if(certified != null) {
				model.addAttribute("certified", certified.getUserId());				
			}
			
			if(user.getProfileImg() == null) {
				model.addAttribute("profileImg", null);
			} else {
				model.addAttribute("profileImg", user.getProfileImg());
			}
		}
		
    	return "gpt/chatgpt";
    }

    @PostMapping("/gpt")
    public String generateText(@RequestParam("prompt") String prompt,
                               @RequestParam("maxTokens") int maxTokens) throws IOException {
    	log.info("##### ChatGPT Page __ API #####");
    	
        // GPT-3 API를 사용하여 텍스트 생성
        String generatedText = gptClient.generateText(prompt, maxTokens);

        // 생성된 텍스트 반환
        return generatedText;
    }
}
