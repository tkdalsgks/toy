package com.project.toy.comment.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.toy.board.adapter.GsonLocalDateTimeAdapter;
import com.project.toy.comment.dto.CommentRequestDTO;
import com.project.toy.comment.dto.CommentResponseDTO;
import com.project.toy.comment.service.CommentService;

@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@GetMapping("/comments/{boardId}")
	public JsonObject getCommentList(@PathVariable("boardId") Long boardId, @ModelAttribute("params") CommentResponseDTO params) {
		JsonObject jsonObj = new JsonObject();
		List<CommentResponseDTO> commentList = commentService.findAll(boardId);
		
		if(CollectionUtils.isEmpty(commentList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(commentList).getAsJsonArray();
			jsonObj.add("commentList", jsonArr);
		}
		
		return jsonObj;
	}
	
	@RequestMapping(value = { "/comments", "/comments/{id}" }, method = { RequestMethod.POST, RequestMethod.PATCH })
	public JsonObject updateComment(@PathVariable(value = "id", required = false) Long id, @RequestBody final CommentRequestDTO params) {
		JsonObject jsonObj = new JsonObject();
		
		try {
			Long updateComment = commentService.updateComment(params);
			jsonObj.addProperty("result", updateComment);
		} catch(DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");
		} catch(Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}
		
		return jsonObj;
	}
}
