package com.project.toy.paging;

import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "페이징 응답")
@Getter
public class PagingResponse<T> {

	private List<T> list = new ArrayList<>();
	private Pagination pagination;
	
	public PagingResponse(List<T> list, Pagination pagination) {
		this.list = list;
		this.pagination = pagination;
	}
}
