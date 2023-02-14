package com.project.toy.likes.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikesDTO {

	private Long likesNo;
	private Long boardId;
	private String userId;
	private LocalDateTime IDate;
	private int likesCnt;
}
