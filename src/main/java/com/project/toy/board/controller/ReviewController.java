package com.project.toy.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.toy.board.dto.ReviewRequestDTO;
import com.project.toy.board.dto.ReviewResponseDTO;
import com.project.toy.board.service.ReviewService;
import com.project.toy.common.dto.MessageDTO;
import com.project.toy.common.dto.SearchDTO;
import com.project.toy.likes.dto.LikesDTO;
import com.project.toy.paging.PagingResponse;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "review", description = "리뷰 게시글 API")
@Controller
@RequiredArgsConstructor
public class ReviewController {
	
	private final UserService userService;
	private final ReviewService reviewService;
	private final HttpSession session;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 게시글 리스트 조회
	 * @param params
	 * @param auth
	 * @param model
	 * @return
	 */
	@Operation(summary = "리스트 조회", description = "게시글 리스트 조회")
	@GetMapping("/review")
	public String list(@ModelAttribute("params") final SearchDTO params, Authentication auth, Model model) {
		log.info("##### Board Page List __ Call #####");
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		if(auth != null) {
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("user", user.getUserNickname());
			model.addAttribute("role", user.getRole());
		}
		
		PagingResponse<ReviewResponseDTO> reviews = reviewService.findAll(params);
		model.addAttribute("reviews", reviews);
		
		
		List<ReviewResponseDTO> notice = reviewService.findNotice(params);
		model.addAttribute("notice", notice);
		 
		
		List<LikesDTO> likes = reviewService.findLikesBestReview(params);
		model.addAttribute("likes", likes);
		
		return "board/review/list";
	}
	
	/**
	 * 게시글 작성 페이지
	 * @param id
	 * @param auth
	 * @param model
	 * @return
	 */
	@Operation(summary = "작성 페이지 조회", description = "게시글 작성 페이지 조회")
	@GetMapping("/review/write")
	public String write(@RequestParam(value = "id", required = false) final Long id, Authentication auth, Model model) {
		log.info("##### Board Page Write __ Call " + id + " #####");
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		if(auth != null) {
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("user", user.getUserNickname());
			model.addAttribute("role", user.getRole());
		}
		
		if(id != null) {
			ReviewResponseDTO review = reviewService.findByReviewId(id);
			model.addAttribute("review", review);
		}
		
		return "board/review/write";
	}
	
	/**
	 * 신규 게시글 생성
	 * @param params
	 * @param model
	 * @return
	 */
	@Operation(summary = "신규 게시글 생성", description = "신규 게시글 생성 메서드")
	@PostMapping("/review/save")
	public String save(final ReviewRequestDTO params, Model model) {
		log.info("##### Board Page Save __ API #####");
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		params.setWriterNo(user.getUserNo());
		params.setWriterId(user.getUserId());
		
		//params.setContent(params.getContent().replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
		reviewService.saveReview(params);
		MessageDTO message = new MessageDTO("게시글 생성이 완료되었습니다.", "/review", RequestMethod.POST, null);
		
		return showMessageAndRedirect(message, model);
	}
	
	/**
	 * 게시글 삭제
	 * @param id
	 * @param queryParams
	 * @param model
	 * @return
	 */
	@Operation(summary = "게시글 삭제", description = "게시글 삭제 메서드")
	@PostMapping("/review/delete")
	public String delete(@RequestParam final Long id, final SearchDTO queryParams, Model model) {
		log.info("##### Board Page Delete __ API " + id + " #####");
		
		reviewService.deleteReview(id);
		MessageDTO message = new MessageDTO("게시글 삭제가 완료되었습니다.", "/review", RequestMethod.GET, queryParamsToMap(queryParams));
		
		return showMessageAndRedirect(message, model);
	}
	
	/**
	 * 성공/실패 메세지를 띄우고 해당 주소 리다이렉트
	 * @param message
	 * @param model
	 * @return
	 */
	private String showMessageAndRedirect(final MessageDTO message, Model model) {
		model.addAttribute("message", message);
		
		return "common/messageRedirect";
	}
	
	/** 
	 * 쿼리 스트링 파라미터를 Map에 담아 반환
	 * @param queryParams
	 * @return
	 */
    private Map<String, Object> queryParamsToMap(final SearchDTO queryParams) {
        Map<String, Object> data = new HashMap<>();
        data.put("page", queryParams.getPage());
        data.put("recordSize", queryParams.getRecordSize());
        data.put("pageSize", queryParams.getPageSize());
        data.put("keyword", queryParams.getKeyword());
        data.put("searchType", queryParams.getSearchType());
        return data;
    }
}
