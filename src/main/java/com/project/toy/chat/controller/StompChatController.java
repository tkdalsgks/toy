package com.project.toy.chat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.project.toy.chat.dto.ChatMessageDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "stomp", description = "채팅 API")
@Controller
@RequiredArgsConstructor
public class StompChatController {
	
	private final SimpMessagingTemplate smt;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Operation(summary = "채팅방 입장 메세지 전송", description = "채팅방 입장 메세지 전송 메서드")
	@MessageMapping("/chat/enter")
	public void enter(ChatMessageDTO message) {
		message.setMessage("[" + message.getWriter() + "] 님이 채팅방에 참여하였습니다.");
		smt.convertAndSend("/sub/chat/entry/" + message.getRoomId(), message);
	}
	
	@MessageMapping("/chat/leave")
	public void leave(ChatMessageDTO message) {
		message.setMessage("[" + message.getWriter() + "] 님이 채팅방에서 퇴장하였습니다.");
		smt.convertAndSend("/sub/chat/leave/" + message.getRoomId(), message);
	}
	
	@Operation(summary = "채팅방 메세지 전송", description = "채팅방 메세지 전송 메서드")
	@MessageMapping("/chat/message")
	public void message(ChatMessageDTO message) {
		log.info("WRITER : {}, " + " CHAT : {}", message.getWriter(), message.getMessage());
		message.setMessage(message.getMessage());
		smt.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	}
}
