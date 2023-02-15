package com.project.toy.common.dto;

import com.project.toy.paging.Pagination;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "검색")
@Getter
@Setter
public class SearchDTO {

	@Schema(description = "현재 페이지 번호")
	private int page;
	
	@Schema(description = "페이지당 출력할 데이터 개수")
	private int recordSize;
	
	@Schema(description = "하단에 출력할 페이지 사이즈")
	private int pageSize;
	
	@Schema(description = "검색 키워드")
	private String keyword;
	
	@Schema(description = "검색 유형")
	private String searchType;
	
	@Schema(description = "페이지네이션 정보")
	private Pagination pagination;
	
	public SearchDTO() {
		this.page = 1;
		this.recordSize = 10;
		this.pageSize = 5;
	}
}
