package com.project.toy.points.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.project.toy.points.dto.GoodsDTO;
import com.project.toy.points.service.PointsService;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/points")
public class GameController {
	
	private final UserService userService;
	private final PointsService pointsService;
	
	private final HttpServletRequest request;
	private final HttpSession session;

	@GetMapping("/box")
	public String pointsBox(Model model) {
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		model.addAttribute("userId", user.getUserId());
		
		String referer = request.getHeader("referer");
		model.addAttribute("referer", referer);
		
		return "points/game/box";
	}
	
	@GetMapping("/goods")
	public String goods(GoodsDTO params, Model model) {
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		model.addAttribute("userId", user.getUserId());
		
		String referer = request.getHeader("referer");
		model.addAttribute("referer", referer);
		
		// 도장 개수
		int goodsCntUser1 = pointsService.selectGoodsUser1(params);
		int goodsCntUser2 = pointsService.selectGoodsUser2(params);
		model.addAttribute("goodsCntUser1", goodsCntUser1);
		model.addAttribute("goodsCntUser2", goodsCntUser2);
		
		// 완성 개수
		int totalCntUser1 = pointsService.totalGoodsUser1(params);
		int totalCntUser2 = pointsService.totalGoodsUser2(params);
		model.addAttribute("totalCntUser1", totalCntUser1);
		model.addAttribute("totalCntUser2", totalCntUser2);
		
		return "points/game/goods";
	}
	
	@PostMapping("/goods")
	public @ResponseBody JsonObject saveGoods(@RequestBody GoodsDTO params) {
		JsonObject jsonObj = new JsonObject();
		
		try {
			boolean result = pointsService.saveGoods(params);
			if(result == true) {
				int goodsCntUser1 = pointsService.selectGoodsUser1(params);
				int goodsCntUser2 = pointsService.selectGoodsUser2(params);
				jsonObj.addProperty("result", result);
				jsonObj.addProperty("goodsCntUser1", goodsCntUser1);
				jsonObj.addProperty("goodsCntUser2", goodsCntUser2);
			}
		} catch(DataAccessException e) {
			jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");
		} catch(Exception e) {
			jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
		}
		
		return jsonObj;
	}
	
	@PostMapping("/reset")
	public @ResponseBody JsonObject resetGoods(@RequestBody GoodsDTO params) {
		JsonObject jsonObj = new JsonObject();
		
		try {
			boolean result = pointsService.resetGoods(params);
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
