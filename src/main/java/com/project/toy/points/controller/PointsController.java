package com.project.toy.points.controller;

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

	@PostMapping("")
	public JsonObject points(@RequestBody PointsRequestDTO params) {
		JsonObject jsonObj = new JsonObject();
		
		try {
			boolean selectPoints = pointsService.selectPoints(params);
			System.out.println("1111111111111111111111111111111 : " + selectPoints);
			if(selectPoints == true) {
				//boolean savePoints = pointsService.savePoints(params);
				//System.out.println("222222222222222222222222222222 : " + savePoints);
				jsonObj.addProperty("result", selectPoints);
			}
		} catch(DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");
		} catch(Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}
		
		return jsonObj;
	}
}
