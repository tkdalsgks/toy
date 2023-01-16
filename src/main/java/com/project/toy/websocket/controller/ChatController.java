package com.project.toy.websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Controller
public class ChatController extends TextWebSocketHandler {

	@GetMapping("/chat")
	public String chat() {

		return "chat/chat";
	}
}
