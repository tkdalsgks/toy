package com.project.toy.chat.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.project.toy.chat.dto.ChatRoomDTO;

@Service
public class ChatRoomService {

	private Map<String, ChatRoomDTO> chatRoomDTO;
	
	@PostConstruct
	private void init() {
		chatRoomDTO = new LinkedHashMap<>();
	}
	
	/**
	 * 전체 채팅방 조회
	 * @return
	 */
	public List<ChatRoomDTO> findAllRooms() {
		// 채팅방 생성 순서 최근 순으로 반환
		List<ChatRoomDTO> result = new ArrayList<>(chatRoomDTO.values());
		Collections.reverse(result);
		
		return result;
	}
	
	/**
	 * roomId 기준으로 채팅방 찾기
	 * @param roomId
	 * @return
	 */
	public ChatRoomDTO findRoomById(String roomId) {
		return chatRoomDTO.get(roomId);
	}
	
	/**
	 * roomName 으로 채팅방 만들기
	 * @param roomName
	 * @return
	 */
	public ChatRoomDTO createChatRoomDTO(String roomName) {
		ChatRoomDTO room = ChatRoomDTO.create(roomName);
		chatRoomDTO.put(room.getRoomId(), room);
		
		return room;
	}
	
	public void plusUserCnt(String roomId) {
		ChatRoomDTO room = chatRoomDTO.get(roomId);
		room.setUserCount(room.getUserCount() + 1);
	}
	
	public void minusUserCnt(String roomId) {
		ChatRoomDTO room = chatRoomDTO.get(roomId);
		room.setUserCount(room.getUserCount() - 1);
	}
	
	public String addUser(String roomId, String userName) {
		ChatRoomDTO room = chatRoomDTO.get(roomId);
		String userUUID = UUID.randomUUID().toString();
		
		// 아이디 중복 확인 후 userList 에 추가
		room.getUserlist().put(userUUID, userName);
		
		return userUUID;
	}
	
	public String isDuplicateName(String roomId, String userName) {
		ChatRoomDTO room = chatRoomDTO.get(roomId);
		String tmp = userName;
		
		// 만약 usetName 이 중복이라면 랜덤한 숫자를 붙임
		// 이때 랜덤한 숫자를 붙였을 때 getUserlist 안에 있는 닉네임이라면 다시 랜덤한 숫자를 붙인다.
		while(room.getUserlist().containsValue(tmp)) {
			int ranNum = (int) (Math.random() * 100) + 1;
			
			tmp = userName + ranNum;
		}
		
		return tmp;
	}
	
	public void delUser(String roomId, String userUUID) {
		ChatRoomDTO room = chatRoomDTO.get(roomId);
		room.getUserlist().remove(userUUID);
	}
	
	public String getUserName(String roomId, String userUUID) {
		ChatRoomDTO room = chatRoomDTO.get(roomId);
		return room.getUserlist().get(userUUID);
	}
	
	public ArrayList<String> getUserList(String roomId) {
		ArrayList<String> list = new ArrayList<>();
		
		ChatRoomDTO room = chatRoomDTO.get(roomId);
		
		room.getUserlist().forEach((key, value) -> list.add(value));
		
		return list;
	}
}
