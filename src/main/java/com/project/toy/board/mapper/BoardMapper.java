package com.project.toy.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.toy.board.dto.BoardRequestDTO;
import com.project.toy.board.dto.BoardResponseDTO;
import com.project.toy.common.dto.SearchDTO;

@Mapper
public interface BoardMapper {

	public List<BoardResponseDTO> findAll(SearchDTO params);
	public List<BoardResponseDTO> findNotice(SearchDTO params);
	public BoardResponseDTO findByBoardId(Long id);
	public void saveBoard(BoardRequestDTO params);
	public void updateBoard(BoardRequestDTO params);
	public void deleteByBoardId(Long id);
	public int countBoard(SearchDTO params);
	public void countHits(Long id);
}
