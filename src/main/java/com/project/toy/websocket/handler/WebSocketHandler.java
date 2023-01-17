package com.project.toy.websocket.handler;

import java.text.ParseException;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
	
	HashMap<String, WebSocketSession> map = new HashMap<>();

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		// 메세지 발송
		String msg = message.getPayload();
		for(String key : map.keySet()) {
			WebSocketSession wss = map.get(key);
			try {
				wss.sendMessage(new TextMessage(msg));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// 소켓 연결
		super.afterConnectionEstablished(session);
		map.put(session.getId(), session);
		JSONObject obj = new JSONObject();
		obj.put("type", "getId");
		obj.put("sessionId", session.getId());
		session.sendMessage(new TextMessage(obj.toJSONString()));
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// 소켓 종료
		map.remove(session.getId());
		super.afterConnectionClosed(session, status);
	}
	
	@SuppressWarnings("unused")
	private static JSONObject JsonToObjectParser(String jsonStr) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		obj = (JSONObject) parser.parse(jsonStr);
		
		return obj;
	}
}
