package com.project.toy.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "게시글 요청")
@Getter
@Setter
public class ReviewRequestDTO {

	@Schema(description = "번호", nullable = false)
	private Long id;
	
	@Schema(description = "제목", nullable = false)
	private String title;
	
	@Schema(description = "내용", nullable = false)
	private String content;
	
	@Schema(description = "작성자 아이디")
	private String writerId;
	
	@Schema(description = "작성자")
	private String writer;
	
	@Schema(description = "공지글 여부")
	private Boolean noticeYn;
}
