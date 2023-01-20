package com.project.toy.chat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.project.toy.chat.dto.ChatMessageDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StompChatController {
	
	private final SimpMessagingTemplate smt;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@MessageMapping("/chat/enter")
	public void enter(ChatMessageDTO message) {
		message.setMessage("[" + message.getWriter() + "]님이 채팅방에 참여하였습니다.");
		smt.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	}
	
	@MessageMapping("/chat/message")
	public void message(ChatMessageDTO message) {
		log.info("CHAT {}", message.toString());
		message.setMessage(message.getMessage());
		smt.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	}
}
