package com.project.toy.chat.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.socket.WebSocketSession;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "채팅방")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDTO {

	@Schema(description = "번호")
	private String roomId;
	
	@Schema(description = "이름")
	private String name;
	
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	public static ChatRoomDTO create(String name) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String now = sdf.format(date);
		
		ChatRoomDTO room = new ChatRoomDTO();
		UUID uuid = UUID.randomUUID();
		
		room.roomId = now + toUnsignedString(uuid.getMostSignificantBits(), 6) + toUnsignedString(uuid.getLeastSignificantBits(), 6);
		room.name = name;
		
		return room;
	}
	
	public static String toUnsignedString(long i, int shift) {
		
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << shift;
        long mask = radix - 1;
        long number = i;
        
        do {
            buf[--charPos] = digits[(int) (number & mask)];
            number >>>= shift;
        } while (number != 0);
        return new String(buf, charPos, (64 - charPos));
    }
	
    final static char[] digits = {
    		
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z', '_', '*' // '.', '-'
    };
}
