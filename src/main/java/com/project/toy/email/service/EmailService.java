package com.project.toy.email.service;

import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
	
	private final JavaMailSender emailSender;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	public static final String ePw = createKey();
	
	private MimeMessage createMessage(String to) throws Exception {
		log.info("보내는 대상 : " + to);
		log.info("생성된 인증번호 : " + ePw);
		MimeMessage message = emailSender.createMimeMessage();
		
		message.addRecipients(RecipientType.TO, to);
		message.setSubject("toy, 회원가입 인증 메일입니다.");
		
		String msg = "";
		msg += "<div align='center' style='margin: 20px;'>";
		msg += "<h3>TOY</h3>";
		msg += "<br>";
		msg += "<p>아래 CODE를 입력해주세요.<p>";
		msg += "<br>";
		msg += "<div style='border: 1px solid black; font-family: 'Gowun Dodum', sans-serif;'>";
		msg += "<div style='font-size: 130%;'>";
		msg += "<h3>CODE : <strong>";
		msg += ePw + "</strong></h3></div></div></div>";
		message.setText(msg, "UTF-8", "html");
		message.setFrom(new InternetAddress("alstkdgks@gmail.com", "TOY"));
		
		return message;
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
}
