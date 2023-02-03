package com.project.toy.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "채팅 메세지")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {

	@Schema(description = "메세지 타입")
	private MessageType type;
	
	@Schema(description = "번호")
	private String roomId;
	
	@Schema(description = "채팅 전송자")
	private String writer;
	
	@Schema(description = "메세지")
	private String message;
}
