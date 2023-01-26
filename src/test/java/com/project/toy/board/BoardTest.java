package com.project.toy.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.toy.board.dto.BoardRequestDTO;
import com.project.toy.board.dto.BoardResponseDTO;
import com.project.toy.board.mapper.BoardMapper;
import com.project.toy.paging.PagingResponse;

@SpringBootTest
public class BoardTest {

	@Autowired
	private BoardMapper boardMapper;
	
	@Test
	void 게시글_저장() {
		BoardRequestDTO boardRequestDTO = new BoardRequestDTO();
		boardRequestDTO.setTitle("게시글 제목");
		boardRequestDTO.setContent("게시글 내용");
		boardRequestDTO.setWriter("테스터");
		boardRequestDTO.setNoticeYn(false);
		boardMapper.saveBoard(boardRequestDTO);
	}
	
	@Test
	void 게시글_찾기() {
		BoardResponseDTO boardResponseDTO = boardMapper.findByBoardId(1L);
		try {
			String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(boardResponseDTO);
			
			System.out.println(boardJson);
		} catch(JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	void 게시글_수정() {
		BoardRequestDTO boardRequestDTO = new BoardRequestDTO();
		boardRequestDTO.setId(1L);
		boardRequestDTO.setTitle("1번 게시글 제목 수정");
		boardRequestDTO.setContent("1번 게시글 내용 수정");
		boardRequestDTO.setWriter("테스터_수정");
		boardRequestDTO.setNoticeYn(true);
		boardMapper.updateBoard(boardRequestDTO);
		
		BoardResponseDTO boardResponseDTO = boardMapper.findByBoardId(1L);
		try {
			String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(boardResponseDTO);
			
			System.out.println(boardJson);
		} catch(JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	void 게시글_삭제() {
		boardMapper.deleteByBoardId(1L);
	}
}
