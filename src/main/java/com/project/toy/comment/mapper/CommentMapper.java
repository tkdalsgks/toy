package com.project.toy.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.toy.comment.dto.CommentRequestDTO;
import com.project.toy.comment.dto.CommentResponseDTO;

@Mapper
public interface CommentMapper {

	// 저장
	public void saveComment(CommentRequestDTO params);
	
	// 상세정보 조회
	public CommentResponseDTO findByCommentId(Long id);
	
	// 수정
	public void updateComment(CommentRequestDTO params);
	
	// 삭제
	public void deleteByCommentId(Long Id);
	
	// 리스트 조회
	public List<CommentResponseDTO> findAll(Long boardId);
	
	// 댓글 수 카운팅
	public int countComment(Long boardId);
	
}
