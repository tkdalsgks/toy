package com.project.toy.likes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.project.toy.likes.dto.LikesDTO;
import com.project.toy.likes.service.LikesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LikesController {

	private final LikesService likesService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 게시글 좋아요, 취소
	 * @param boardId
	 * @param params
	 * @return
	 */
	@PostMapping({"/community/likes", "/community/likes/{boardId}"})
	public JsonObject saveLikes(@PathVariable(value = "boardId", required = false) Long boardId, @RequestBody final LikesDTO params) {
		log.info("##### Likes Page Save __ API #####");
		
		JsonObject jsonObj = new JsonObject();
		
		synchronized(this) {
			try {
				int todayLikes = likesService.selectTodayLikes(params);
				if(todayLikes >= 5) {
					jsonObj.addProperty("todayLikes", todayLikes);
					jsonObj.addProperty("message", "좋아요는 하루 5개의 게시글만 가능합니다.");
				} else {
					boolean save = likesService.saveOrDeleteLikes(params);
					likesService.updateCountLikes(params);
					jsonObj.addProperty("result", save);				
				}
			} catch(DataAccessException e) {
				jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");
			} catch(Exception e) {
				jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
			}
		}
		
		return jsonObj;
	}
}
