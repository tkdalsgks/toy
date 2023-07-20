package com.project.toy.comment.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class CommentResponseDTO {

	private Long id;
	
	private String boardId;
	
	private String boardSeq;
	
	private String content;
	
	private String writerNo;
	
	private String writer;
	
	private String writerId;
	
	private int rating;
	
	private String deleteYn;
	
	private LocalDateTime IDate;
	
	private LocalDateTime UDate;
}
