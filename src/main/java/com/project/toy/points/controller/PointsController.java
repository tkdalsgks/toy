package com.project.toy.points.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.project.toy.points.dto.PointsRequestDTO;
import com.project.toy.points.service.PointsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/points")
public class PointsController {
	
	private final PointsService pointsService;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@PostMapping("")
	public JsonObject points(@RequestBody PointsRequestDTO params) {
		log.info("##### Points Save __ API #####");
		
		JsonObject jsonObj = new JsonObject();
		
		try {
			boolean result = pointsService.selectPoints(params);
			if(result == true) {
				jsonObj.addProperty("result", result);
			}
		} catch(DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");
		} catch(Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}
		
		return jsonObj;
	}
}
