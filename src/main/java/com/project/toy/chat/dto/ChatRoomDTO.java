package com.project.toy.chat.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.socket.WebSocketSession;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "채팅방")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {

	@Schema(description = "번호")
	private String roomId;
	
	@Schema(description = "이름")
	private String name;
	
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	public static ChatRoomDTO create(String name) {
		ChatRoomDTO room = new ChatRoomDTO();
		room.roomId = UUID.randomUUID().toString();
		room.name = name;
		
		return room;
	}
}
