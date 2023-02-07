package com.project.toy.comment.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class CommentResponseDTO {

	private Long id;
	
	private String boardId;
	
	private String content;
	
	private String writer;
	
	private Boolean deleteYn;
	
	private LocalDateTime IDate;
	
	private LocalDateTime UDate;
}
