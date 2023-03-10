package com.project.toy.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.project.toy.board.dto.BoardRequestDTO;
import com.project.toy.board.dto.BoardResponseDTO;
import com.project.toy.board.service.BoardService;
import com.project.toy.chat.service.ChatRoomService;
import com.project.toy.comment.service.CommentService;
import com.project.toy.common.dto.MessageDTO;
import com.project.toy.common.dto.SearchDTO;
import com.project.toy.likes.dto.LikesDTO;
import com.project.toy.likes.service.LikesService;
import com.project.toy.paging.PagingResponse;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "community", description = "게시글 API")
@Controller
@RequiredArgsConstructor
public class BoradController {
	
	private final UserService userService;
	private final BoardService boardService;
	private final CommentService commentService;
	private final ChatRoomService chatRoomService;
	private final LikesService likesService;
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
	@GetMapping("/community")
	public String list(@ModelAttribute("params") final SearchDTO params, Authentication auth, Model model) {
		log.info("***** Community Page Call *****");
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		if(auth != null) {
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("user", user.getUserNickname());
			model.addAttribute("list", chatRoomService.findAllRooms());
		}
		
		PagingResponse<BoardResponseDTO> boards = boardService.findAll(params);
		model.addAttribute("boards", boards);
		
		List<BoardResponseDTO> notice = boardService.findNotice(params);
		model.addAttribute("notice", notice);
		
		List<LikesDTO> likes = boardService.findLikesBest(params);
		model.addAttribute("likes", likes);
		
		return "board/community/list";
	}
	
	/**
	 * 게시글 상세페이지 조회
	 * @param id
	 * @param auth
	 * @param model
	 * @return
	 */
	@Operation(summary = "상세페이지 조회", description = "게시글 상세페이지 조회")
	@Transactional
	@GetMapping("/community/detail")
	public String detail(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response, Authentication auth, Model model) {
		log.info("# Community Page Detail?id = " + id);
		
		Cookie oldCookie = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if(cookie.getName().equals("boardView")) {
					oldCookie = cookie;
				}
			}
		}
		
		if(oldCookie != null) {
			if(!oldCookie.getValue().contains("[" + id.toString() + "]")) {
				boardService.countHits(id);
				oldCookie.setValue(oldCookie.getValue() + "[" + id + "]");
				oldCookie.setPath("/");
				oldCookie.setMaxAge(60 * 60 * 24);
				response.addCookie(oldCookie);
			}
		} else {
			boardService.countHits(id);
			Cookie newCookie = new Cookie("boardView", "[" + id + "]");
			newCookie.setPath("/");
			newCookie.setMaxAge(60 * 60 * 24);
			response.addCookie(newCookie);
		}
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		if(auth != null) {
			model.addAttribute("user", user.getUserNickname());
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("userName", user.getUserNickname());
			model.addAttribute("list", chatRoomService.findAllRooms());
		}
		
		BoardResponseDTO board = boardService.findByBoardId(id);
		LikesDTO likesDTO = new LikesDTO();
		
		if(board != null) {
			model.addAttribute("board", board);
			
			likesDTO.setBoardId(board.getId());
			likesDTO.setUserId(user.getUserId());
			
			int comment = commentService.countComment(id);
			int likesAll = likesService.selectLikes(likesDTO);
			
			if(comment != 0) {
				model.addAttribute("comment", comment);
			} else {
				model.addAttribute("comment", "0");
			}
			
			if(likesAll != 0) {
				model.addAttribute("likesAll", likesAll);
			} else {
				model.addAttribute("likesAll", likesAll);
			}
			
			if(likesService.findLikes(likesDTO) == 0) {
				model.addAttribute("likes", 0);
			} else {
				model.addAttribute("likes", 1);
			}
		} else {
			MessageDTO message = new MessageDTO("존재하지 않거나 이미 삭제된 게시글입니다.", "/community", RequestMethod.POST, null);
			return showMessageAndRedirect(message, model);
		}
		
		return "board/community/detail";
	}
	
	/**
	 * 게시글 작성 페이지
	 * @param id
	 * @param auth
	 * @param model
	 * @return
	 */
	@Operation(summary = "작성 페이지 조회", description = "게시글 작성 페이지 조회")
	@GetMapping("/community/write")
	public String write(@RequestParam(value = "id", required = false) final Long id, Authentication auth, Model model) {
		log.info("# Community Page Write?id = " + id);
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		if(auth != null) {
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("user", user.getUserNickname());
			model.addAttribute("list", chatRoomService.findAllRooms());
		}
		
		if(id != null) {
			BoardResponseDTO board = boardService.findByBoardId(id);
			model.addAttribute("board", board);
		}
		
		return "board/community/write";
	}
	
	/**
	 * 게시글 수정 페이지
	 * @param id
	 * @param auth
	 * @param model
	 * @return
	 */
	@Operation(summary = "수정 페이지 조회", description = "게시글 수정 페이지 조회")
	@GetMapping("/community/change")
	public String change(@RequestParam(value = "id", required = false) final Long id, Authentication auth, Model model) {
		log.info("# Community Page Write?id = " + id);
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		if(auth != null) {
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("user", user.getUserNickname());
			model.addAttribute("list", chatRoomService.findAllRooms());
		}
		
		if(id != null) {
			BoardResponseDTO board = boardService.findByBoardId(id);
			model.addAttribute("board", board);
		}
		
		return "board/community/change";
	}
	
	/**
	 * 신규 게시글 생성
	 * @param params
	 * @param model
	 * @return
	 */
	@Operation(summary = "신규 게시글 생성", description = "신규 게시글 생성 메서드")
	@PostMapping("/community/save")
	public String save(final BoardRequestDTO params, Model model) {
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		params.setWriterId(user.getUserId());
		
		//params.setContent(params.getContent().replaceAll("(\r\n|\r|\n|\n\r)", "<br>"));
		boardService.saveBoard(params);
		MessageDTO message = new MessageDTO("게시글 생성이 완료되었습니다.", "/community", RequestMethod.POST, null);
		
		return showMessageAndRedirect(message, model);
	}
	
	/**
	 * 기존 게시글 수정
	 * @param params
	 * @param model
	 * @return
	 */
	@Operation(summary = "기존 게시글 수정", description = "기존 게시글 수정 메서드")
	@PostMapping("/community/update")
	public String update(final BoardRequestDTO params, Model model) {
		boardService.updateBoard(params);
		MessageDTO message = new MessageDTO("게시글 수정이 완료되었습니다.", "/community", RequestMethod.POST, null);
		
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
	@PostMapping("/community/delete")
	public String delete(@RequestParam final Long id, final SearchDTO queryParams, Model model) {
		boardService.deleteBoard(id);
		MessageDTO message = new MessageDTO("게시글 삭제가 완료되었습니다.", "/community", RequestMethod.GET, queryParamsToMap(queryParams));
		
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
