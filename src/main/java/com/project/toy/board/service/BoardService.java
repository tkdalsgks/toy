package com.project.toy.board.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.toy.board.dto.BoardRequestDTO;
import com.project.toy.board.dto.BoardResponseDTO;
import com.project.toy.board.mapper.BoardMapper;
import com.project.toy.common.dto.SearchDTO;
import com.project.toy.paging.Pagination;
import com.project.toy.paging.PagingResponse;

@Service
public class BoardService {

	@Autowired
	private BoardMapper boardMapper;
	
	public PagingResponse<BoardResponseDTO> findAll(final SearchDTO params) {
		int count = boardMapper.countBoard(params);
		if(count < 1) {
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		
		Pagination pagination = new Pagination(count, params);
		params.setPagination(pagination);
		
		List<BoardResponseDTO> list = boardMapper.findAll(params);
		
		return new PagingResponse<>(list, pagination);
	}
	
	public List<BoardResponseDTO> findNotice(SearchDTO params) {
		return boardMapper.findNotice(params);
	}
	
	public BoardResponseDTO findByBoardId(Long id) {
		return boardMapper.findByBoardId(id);
	}

	public void saveBoard(BoardRequestDTO params) {
		boardMapper.saveBoard(params);
	}
	
	public void updateBoard(BoardRequestDTO params) {
		boardMapper.updateBoard(params);
	}
	
	public void deleteBoard(Long id) {
		boardMapper.deleteByBoardId(id);
	}
	
	public void countHits(Long id) {
		boardMapper.countHits(id);
	}

}
