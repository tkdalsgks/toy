package com.project.toy.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {

	private MessageType type;	// 메세지 타입
	private String roomId;		// 방 번호
	private String writer;		// 채팅 보낸 사람
	private String message;		// 메세지
}
