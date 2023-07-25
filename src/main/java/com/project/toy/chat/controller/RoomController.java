package com.project.toy.chat.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.JsonObject;
import com.project.toy.chat.dto.ChatRoomDTO;
import com.project.toy.chat.service.ChatRoomService;
import com.project.toy.user.dto.SessionUser;
import com.project.toy.user.dto.UserDTO;
import com.project.toy.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "chat", description = "채팅 API")
@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class RoomController {

	private final UserService userService;
	private final ChatRoomService chatRoomService;
	
	private final HttpSession session;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 채팅방 리스트 조회
	 * @param roomId
	 * @param auth
	 * @param model
	 */
	@Operation(summary = "채팅방 리스트 조회", description = "채팅방 리스트 조회")
	@ResponseBody
	@PostMapping("/list")
	public List<ChatRoomDTO> createRoom(String roomId, Authentication auth, Model model) {
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		
		model.addAttribute("userId", user.getUserId());
		model.addAttribute("user", user.getUserNickname());
		
		List<ChatRoomDTO> roomList = chatRoomService.findAllRooms();
		model.addAttribute("roomList", roomList);
		
		return roomList;
	}
	
	/**
	 * 채팅방 개설
	 * @param name
	 * @param rttr
	 * @param model
	 * @return
	 */
	@Operation(summary = "채팅방 개설", description = "채팅방 개설 메서드")
	@ResponseBody
	@PostMapping("/room")
	public JsonObject create(@RequestParam String name, RedirectAttributes rttr, Model model) {
		log.info("# Create Chat Room, name : " + name);
		
		JsonObject jsonObj = new JsonObject();
		
		ChatRoomDTO roomName = chatRoomService.createChatRoomDTO(name);
		jsonObj.addProperty("roomId", roomName.getRoomId());
		jsonObj.addProperty("roomName", roomName.getName());
		System.out.println("roomName : " + roomName.getName() + " roomId : "+ roomName.getRoomId());
		
		return jsonObj;
	}
	
	@GetMapping("/room")
	public void getRoom(String roomId, Authentication auth, Model model) {
		log.info("# get Chat Room, roomId : " + roomId);
		
		SessionUser sessionUser = (SessionUser) session.getAttribute("user");
		UserDTO user = userService.findByUserId(sessionUser.getUserEmail());
		if(auth != null) {
			model.addAttribute("userId", user.getUserId());
			model.addAttribute("user", user.getUserNickname());
			model.addAttribute("role", user.getRole());
		}
		
		model.addAttribute("room", chatRoomService.findRoomById(roomId));
	}
}
