package com.project.toy.email.controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.toy.common.dto.MessageDTO;
import com.project.toy.email.service.EmailService;
import com.project.toy.user.dto.CertifiedUserDTO;
import com.project.toy.user.dto.Role;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UserDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "email", description = "이메일 인증 API")
@Controller
@RequiredArgsConstructor
public class EmailController {

	private final EmailService emailService;
	
	private final HttpSession session;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@ResponseBody
	@PostMapping("/check/mail")
	public String checkMail(@RequestParam("userEmail") String userEmail) throws Exception {
		String code = emailService.sendSimpleMessage(userEmail);
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
	
	/**
	 * 계정 최초 1회 이메일 인증으로 권한 GUEST -> USER
	 * @param params
	 * @param userDTO
	 * @param model
	 * @return
	 */
	@GetMapping("/certified")
	public String certifiedEmail(CertifiedUserDTO params, UserDTO userDTO, Model model) {
		MessageDTO message;
		
		SessionUser user = (SessionUser) session.getAttribute("user");
		params.setUserId(user.getUserId());
		userDTO.setUserId(params.getUserId());
		
		CertifiedUserDTO certified = emailService.selectCertifiedEmail(params);
		LocalDateTime IDate = certified.getIDate();
		LocalDateTime now = LocalDateTime.now();
		long minutes = ChronoUnit.MINUTES.between(IDate, now);
		
		if(certified.getCertifiedCode().equals(EmailService.ePwRand)) {
			if(minutes < 1) {
				// 현재 가지고 있는 Authentication 정보 호출
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				
				// 기존 정보 삭제 후 새로운 권한 추가
				Collection<GrantedAuthority> updatedAuthorities = new ArrayList<>(authentication.getAuthorities());
				updatedAuthorities.clear();
				updatedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
				
				// 새로운 권한을 Security에 저장
				Authentication newAuth = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), updatedAuthorities);
				SecurityContextHolder.getContext().setAuthentication(newAuth);
				
				// 세션에 저장된 권한을 GUEST -> USER 로 변경
				user.setRole(Role.USER);
				
				emailService.successCertifiedEmail(params);
				emailService.successCertifiedRole(userDTO);
				message = new MessageDTO("이메일 인증이 완료되었습니다.\n이제부터 OYEZ의 모든 콘텐츠를 즐기실 수 있습니다.", "/", RequestMethod.GET, null);
				
				return showMessageAndRedirect(message, model);
			} else {
				message = new MessageDTO("인증코드 유효시간이 지났습니다.\n다시 인증해주세요.", "/", RequestMethod.GET, null);
				
				return showMessageAndRedirect(message, model);
			}
		} else {
			message = new MessageDTO("인증코드가 일치하지 않습니다.\n링크가 올바른지 확인하거나 다시 인증해주세요.", "/", RequestMethod.GET, null);
			
			return showMessageAndRedirect(message, model);
		}
	}
	
	/**
	 * 성공/실패 메세지를 띄우고 해당 주소 리다이렉트
	 * @param message
	 * @param model
	 * @return
	 */
	private String showMessageAndRedirect(final MessageDTO message, Model model) {
		model.addAttribute("message", message);
		
		return "common/messageRedirect";
	}
}
