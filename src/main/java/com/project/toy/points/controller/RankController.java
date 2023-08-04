package com.project.toy.points.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.toy.points.dto.RankDTO;
import com.project.toy.points.service.PointsService;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RankController {
	
	private final PointsService pointsService;
	private final UserService userService;
	
	private final HttpSession session;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/rank")
	public String rank(Authentication auth, Model model) {
		log.info("##### Points Rank Page __ Call #####");
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		if(auth != null) {
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("user", user.getUserNickname());
		}
		
		// 활동점수 랭킹
		List<RankDTO> rankTotalList = pointsService.rankingTotalPoints();
		List<RankDTO> rankWeekList = pointsService.rankingWeekPoints();
		model.addAttribute("total", rankTotalList);
		model.addAttribute("week", rankWeekList);
		
		return "points/rank";
	}
}
