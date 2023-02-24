package com.project.toy.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.toy.comment.dto.CommentRequestDTO;
import com.project.toy.comment.dto.CommentResponseDTO;
import com.project.toy.comment.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentMapper commentMapper;
	
	public CommentResponseDTO findByCommentId(final Long id) {
		return commentMapper.findByCommentId(id);
	}
	
	@Transactional
	public boolean saveComment(CommentRequestDTO params) {
		int queryResult = 0;

		if (params.getId() == null) {
			queryResult = commentMapper.saveComment(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Transactional
	public boolean updateComment(CommentRequestDTO params) {
		int queryResult = 0;

		if (params.getId() != null) {
			queryResult = commentMapper.updateComment(params);
		}

		return (queryResult == 1) ? true : false;
	}
	
	@Transactional
	public boolean deleteComment(Long id) {
		int queryResult = 0;
		
		CommentResponseDTO comment = commentMapper.findByCommentId(id);
		
		if (comment != null && "N".equals(comment.getDeleteYn())) {
			queryResult = commentMapper.deleteComment(id);
		}

		return (queryResult == 1) ? true : false;
	}
	
	public List<CommentResponseDTO> findAll(final Long boardId) {
		return commentMapper.findAll(boardId);
	}
	
	public int countComment(final Long boardId) {
		return commentMapper.countComment(boardId);
	}
	
	public void updateCountComment(CommentRequestDTO params) {
		commentMapper.updateCountComment(params);
	}
}
