package com.project.toy.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDTO {

	private Long id;
	private String title;		// 제목
	private String content;		// 내용
	private String writerId;	// 작성자 아이디
	private String writer;		// 작성자
	private Boolean noticeYn;	// 공지글 여부
}
