package com.project.toy.comment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDTO {

	private Long id;
	private String boardId;
	private String content;
	private String writer;
	private String writerId;
	private int rating;
}
