package com.project.toy.comment.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.toy.board.adapter.GsonLocalDateTimeAdapter;
import com.project.toy.comment.dto.CommentRequestDTO;
import com.project.toy.comment.dto.CommentResponseDTO;
import com.project.toy.comment.service.CommentService;
import com.project.toy.user.dto.SessionUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "comments", description = "댓글 API")
@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private HttpSession session;
	
	/**
	 * 댓글 리스트 조회
	 * @param boardId
	 * @param params
	 * @param model
	 * @return
	 */
	@Operation(summary = "리스트 조회", description = "댓글 리스트 조회")
	@GetMapping("/comments/{boardId}")
	public JsonObject getCommentList(@PathVariable("boardId") Long boardId, @ModelAttribute("params") CommentResponseDTO params, Model model) {
		JsonObject jsonObj = new JsonObject();
		List<CommentResponseDTO> commentList = commentService.findAll(boardId);
		
		if(CollectionUtils.isEmpty(commentList) == false) {
			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
			JsonArray jsonArr = gson.toJsonTree(commentList).getAsJsonArray();
			jsonObj.add("commentList", jsonArr);
		}
		
		return jsonObj;
	}
	
	/**
	 * 댓글 작성
	 * @param id
	 * @param params
	 * @return
	 */
	@Operation(summary = "댓글 작성", description = "댓글 작성 메서드")
	@PostMapping({"/comments", "/comments/{id}"})
	public JsonObject saveComment(@PathVariable(value = "id", required = false) Long id, @RequestBody final CommentRequestDTO params) {
		JsonObject jsonObj = new JsonObject();
		
		try {
			boolean save = commentService.saveComment(params);
			commentService.updateCountComment(params);
			jsonObj.addProperty("result", save);
		} catch(DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");
		} catch(Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}
		
		return jsonObj;
	}
	
	/**
	 * 댓글 수정
	 * @param id
	 * @param params
	 * @return
	 */
	@Operation(summary = "댓글 수정", description = "댓글 수정 메서드")
	@PatchMapping({"/comments", "/comments/{id}"})
	public JsonObject updateComment(@PathVariable(value = "id", required = false) Long id, @RequestBody final CommentRequestDTO params) {
		JsonObject jsonObj = new JsonObject();
		
		try {
			SessionUser user = (SessionUser) session.getAttribute("user");
			CommentResponseDTO comment = commentService.findByCommentId(id);
			
			if(user.getUserId().equals(comment.getWriterId())) {
				boolean update = commentService.updateComment(params);
				jsonObj.addProperty("result", update);
			} else {
				jsonObj.addProperty("result", "isUnauthorized");
				jsonObj.addProperty("message", "권한이 없는 댓글입니다.");
			}
		} catch(DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");
		} catch(Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}
		
		return jsonObj;
	}
	
	/**
	 * 댓글 삭제
	 * @param id
	 * @return
	 */
	@Operation(summary = "댓글 삭제", description = "댓글 삭제 메서드")
	@DeleteMapping("/comments/{id}")
	public JsonObject deleteComment(@PathVariable("id") final Long id) {
		JsonObject jsonObj = new JsonObject();
		
		try {
			SessionUser user = (SessionUser) session.getAttribute("user");
			CommentResponseDTO comment = commentService.findByCommentId(id);
			
			if(user.getUserId().equals(comment.getWriterId())) {
				boolean delete = commentService.deleteComment(id);
				jsonObj.addProperty("result", delete);
				jsonObj.addProperty("message", "댓글이 삭제되었습니다.");
			} else {
				jsonObj.addProperty("result", "isUnauthorized");
				jsonObj.addProperty("message", "권한이 없는 댓글입니다.");
			}
		} catch(DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");
		} catch(Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}
		
		return jsonObj;
	}
}
