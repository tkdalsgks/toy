package com.project.toy.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.toy.comment.dto.CommentRequestDTO;
import com.project.toy.comment.dto.CommentResponseDTO;

@Mapper
public interface CommentMapper {

	public CommentResponseDTO findByCommentId(Long id);
	public int saveComment(CommentRequestDTO params);
	public int updateComment(CommentRequestDTO params);
	public int deleteComment(Long id);
	public List<CommentResponseDTO> findAll(Long boardId);
	public int countComment(Long boardId);
	public void updateCountComment(CommentRequestDTO params);
}
