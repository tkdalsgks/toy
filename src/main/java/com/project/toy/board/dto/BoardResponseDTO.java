package com.project.toy.board.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class BoardResponseDTO {

	private Long id;
	private String title;			// 제목
	private String content;			// 내용
	private String writer;			// 작성자
	private int viewCnt;			// 조회수
	private Boolean noticeYn;		// 공지글 여부
	private Boolean deleteYn;		// 삭제 여부
	private LocalDateTime IDate;	// 생성일시
	private LocalDateTime UDate;	// 수정일시
}
