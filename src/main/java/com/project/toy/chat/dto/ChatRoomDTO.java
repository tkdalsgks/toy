package com.project.toy.chat.dto;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.socket.WebSocketSession;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {

	private String roomId;	// 채팅방 아이디
	private String name;	// 채팅방 이름
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	public static ChatRoomDTO create(String name) {
		ChatRoomDTO room = new ChatRoomDTO();
		room.roomId = UUID.randomUUID().toString();
		room.name = name;
		
		return room;
	}
}
