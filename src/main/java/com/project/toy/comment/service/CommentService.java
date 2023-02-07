package com.project.toy.comment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.toy.comment.dto.CommentRequestDTO;
import com.project.toy.comment.dto.CommentResponseDTO;
import com.project.toy.comment.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	@Autowired
	private CommentMapper commentMapper;
	
	// 저장
	@Transactional
	public Long saveComment(final CommentRequestDTO params) {
		commentMapper.saveComment(params);
		
		return params.getId();
	}
	
	// 상세정보 조회
	public CommentResponseDTO findByCommentId(final Long id) {
		return commentMapper.findByCommentId(id);
	}
	
	// 수정
	@Transactional
	public Long updateComment(final CommentRequestDTO params) {
		commentMapper.updateComment(params);
		
		return params.getId();
	}
	
	// 삭제
	@Transactional
	public Long deleteComment(final Long id) {
		commentMapper.deleteByCommentId(id);
		
		return id;
	}
	
	// 리스트 조회
	public List<CommentResponseDTO> findAll(final Long boardId) {
		return commentMapper.findAll(boardId);
	}
}
