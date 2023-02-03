package com.project.toy.email.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.toy.email.service.EmailService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "email", description = "이메일 인증 API")
@Controller
public class EmailController {

	@Autowired
	private EmailService mailService;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@ResponseBody
	@PostMapping("/check/mail")
	public String checkMail(@RequestParam("userEmail") String userEmail) throws Exception {
		String code = mailService.sendSimpleMessage(userEmail);
		log.info("전송된 인증번호 : " + code);
		
		return code;
	}
	
	@ResponseBody
	@PostMapping("/check/verifyCode")
	public int verifyCode(@RequestParam("code") String code) {
		int result = 0;
		log.info("code : " + code);
		log.info("code match : " + EmailService.ePw.equals(code));
		
		if(EmailService.ePw.equals(code)) {
			result = 1;
		}
		
		return result;
	}
}
