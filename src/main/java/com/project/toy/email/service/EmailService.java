package com.project.toy.email.service;

import java.util.Random;
import java.util.UUID;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.toy.user.dto.CertifiedUserDTO;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	
	private final UserMapper userMapper;
	private final JavaMailSender emailSender;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	public static final String ePw = createKey();
	public static final String ePwRand = UUID.randomUUID().toString();
	
	private MimeMessage createMessage(String to) throws Exception {
		log.info("보내는 대상 : " + to);
		log.info("생성된 인증번호 : " + ePw);
		MimeMessage message = emailSender.createMimeMessage();
		
		message.addRecipients(RecipientType.TO, to);
		message.setSubject("OYEZ - 이메일 인증 메일입니다.");
		
		String msg = "";
		msg += "<div align='center' style='margin: 20px;'>";
		msg += "<h3>OYEZ</h3>";
		msg += "<br>";
		msg += "<p>아래 CODE를 입력해주세요.<p>";
		msg += "<br>";
		msg += "<div style='border: 1px solid black; font-family: 'Gowun Dodum', sans-serif;'>";
		msg += "<div style='font-size: 130%;'>";
		msg += "<h3>CODE : <strong>";
		msg += ePw + "</strong></h3></div></div></div>";
		message.setText(msg, "UTF-8", "html");
		message.setFrom(new InternetAddress("alstkdgks@gmail.com", "OYEZ"));
		
		return message;
	}
	
	public void certifiedEmail(UserDTO params, String to) throws Exception {
		log.info("보내는 대상 : " + to);
		log.info("생성된 인증번호 : " + ePwRand);
		
		CertifiedUserDTO certified = new CertifiedUserDTO();
		certified.setUserId(params.getUserId());
		certified.setCertifiedCode(ePwRand);
		userMapper.certifiedEmail(certified);
		
		MimeMessage message = emailSender.createMimeMessage();
		
		message.addRecipients(RecipientType.TO, to);
		message.setSubject("OYEZ - 계정권한 인증 메일입니다.");
		
		String msg = "";
		msg += "<div align='center' style='margin: 20px;'>";
		msg += "<h3>OYEZ</h3>";
		msg += "<br>";
		msg += "<p>아래 [이메일 인증 확인] 버튼을 눌러주세요.<p>";
		msg += "<p></p>";
		msg += "<a href='https://oyez.kr/certified?email=" + params.getUserEmail() + "&ePw=" + ePwRand;
		msg += "' target='_blank'>이메일 인증 확인</a>";
		message.setText(msg, "UTF-8", "html");
		message.setFrom(new InternetAddress("alstkdgks@gmail.com", "OYEZ"));
		
		try {
			emailSender.send(message);
		} catch(MailException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	public void updateUserPwd(UserDTO params, String to) throws Exception {
		log.info("보내는 대상 : " + to);
		log.info("생성된 비밀번호 : " + ePw);
		
		String newPwd = ePw;
		String encPwd = bCryptPasswordEncoder.encode(newPwd);
		
		params.setUserPwd(encPwd);
		
		userMapper.updateUserPwd(params);
		
		MimeMessage message = emailSender.createMimeMessage();
		
		message.addRecipients(RecipientType.TO, to);
		message.setSubject("OYEZ - 임시 비밀번호 발급 메일입니다.");
		
		String msg = "";
		msg += "<div align='center' style='margin: 20px;'>";
		msg += "<h3>OYEZ</h3>";
		msg += "<br>";
		msg += "<p>아래 발급된 임시 비밀번호를 이용하여 로그인 하세요.<p>";
		msg += "<br>";
		msg += "<div style='border: 1px solid black; font-family: 'Gowun Dodum', sans-serif;'>";
		msg += "<div style='font-size: 130%;'>";
		msg += "<h3><strong>";
		msg += ePw + "</strong></h3></div></div></div>";
		message.setText(msg, "UTF-8", "html");
		message.setFrom(new InternetAddress("alstkdgks@gmail.com", "OYEZ"));
		
		try {
			emailSender.send(message);
		} catch(MailException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
	}
	
	public static String createKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();
		
		for(int i = 0; i < 8; i++) {	// 인증코드 8자리
			int index = rnd.nextInt(3);	// 0-2 까지 랜덤
			
			switch(index) {
			case 0:
				key.append((char) ((int) (rnd.nextInt(26)) + 97));
				// a-z  (ex. 1+97+98 -> (char)98 = 'b')
				break;
				
			case 1:
				key.append((char) ((int) (rnd.nextInt(26)) + 65));
				// A-Z
				break;
				
			case 2:
				key.append((rnd.nextInt(10)));
				// 0-9
				break;
			}
		}
		return key.toString();
	}
	
	public String sendSimpleMessage(String to) throws Exception {
		MimeMessage message = createMessage(to);
		
		try {
			emailSender.send(message);
		} catch(MailException e) {
			e.printStackTrace();
			throw new IllegalArgumentException();
		}
		
		return ePw;
	}

	public CertifiedUserDTO selectCertifiedEmail(CertifiedUserDTO params) {
		return userMapper.selectCertifiedEmail(params);
	}
	
	public void successCertifiedEmail(CertifiedUserDTO params) {
		userMapper.successCertifiedEmail(params);
	}

	public void successCertifiedRole(UserDTO userDTO) {
		userMapper.successCertifiedRole(userDTO);
	}
}
