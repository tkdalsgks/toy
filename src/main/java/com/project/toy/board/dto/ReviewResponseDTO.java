package com.project.toy.board.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "게시글 응답")
@Getter
public class ReviewResponseDTO {

	@Schema(description = "번호", nullable = false)
	private Long id;
	
	@Schema(description = "제목", nullable = false)
	private String title;
	
	@Schema(description = "내용", nullable = false)
	private String content;
	
	private String writerNo;
	
	@Schema(description = "작성자 아이디")
	private String writerId;
	
	@Schema(description = "작성자")
	private String writer;
	
	private String profileImg;
	
	private String filterId;
	
	private String filter;
	
	private String hashtag;
	
	@Schema(description = "별점")
	private int rating;
	
	@Schema(description = "조회 수")
	private int viewCnt;
	
	@Schema(description = "댓글 수")
	private int commentCnt;
	
	@Schema(description = "좋아요 수")
	private int likesCnt;
	
	@Schema(description = "공지글 여부")
	private Boolean noticeYn;
	
	private String privateYn;
	
	@Schema(description = "삭제 여부")
	private Boolean deleteYn;
	
	@Schema(description = "생성일시", defaultValue = "NOW()")
	private LocalDateTime IDate;
	
	@Schema(description = "수정일시", defaultValue = "NOW()")
	private LocalDateTime UDate;
}
