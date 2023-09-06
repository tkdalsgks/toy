package com.project.toy.board.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.toy.board.dto.BoardRequestDTO;
import com.project.toy.board.dto.BoardResponseDTO;
import com.project.toy.board.mapper.BoardMapper;
import com.project.toy.common.dto.SearchDTO;
import com.project.toy.likes.dto.LikesDTO;
import com.project.toy.paging.Pagination;
import com.project.toy.paging.PagingResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardMapper boardMapper;
	
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
	
	public List<LikesDTO> findLikesBestCommu(SearchDTO params) {
		return boardMapper.findLikesBestCommu(params);
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

	public void saveHashtag(BoardRequestDTO params) {
		boardMapper.saveHashtag(params);
	}

	public void publicBoard(Long id) {
		boardMapper.publicBoard(id);
	}

	public void privateBoard(Long id) {
		boardMapper.privateBoard(id);
	}
}
