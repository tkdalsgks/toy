package com.project.toy.chat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.toy.chat.dto.ChatMessageDTO;
import com.project.toy.chat.dto.ChatResponseDTO;
import com.project.toy.chat.dto.ChatRoomDTO;

@Mapper
public interface ChatRoomMapper {

	void insertChatRoom(ChatRoomDTO params);

	void insertChatMessage(ChatMessageDTO params);

	List<ChatResponseDTO> listChatMessage(String roomId);

}
