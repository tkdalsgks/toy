package com.project.toy.chat.dto;

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
public class ChatResponseDTO {

	private String roomId;
	private String name;
	private String message;
	private String writer;
	private String writerId;
	private String IDate;
}
