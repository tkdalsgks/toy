package com.project.toy.security.oauth.config;

import java.util.Map;

import com.project.toy.user.dto.Role;
import com.project.toy.user.dto.UserDTO;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String userId;
	private String userEmail;
	private String userNickname;
	private String provider;
	private Role role;
	
	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String userId, String userNickname, String provider, String userEmail, Role role) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.userId = userId;
		this.userNickname = userNickname;
		this.userEmail = userEmail;
		this.provider = provider;
		this.role = role;
	}
	
	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
		switch(registrationId) {
		case "kakao":
			return ofKakao(userNameAttributeName, attributes);
		case "google":
			return ofGoogle(userNameAttributeName, attributes);
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
		Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
		Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
		
		String kakaoEmail = (String) kakaoAccount.get("email");
		String[] kakaoId = kakaoEmail.split("@");
		String userId = kakaoId[0];
		
		return OAuthAttributes.builder()
				.userId(userId)
				.userNickname((String) kakaoProfile.get("nickname"))
				.userEmail((String) kakaoAccount.get("email"))
				.provider("kakao")
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
	}
	
	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		String googleEmail = (String) attributes.get("email");
		String[] googleId = googleEmail.split("@");
		String userId = googleId[0];
		
		return OAuthAttributes.builder()
				.userId(userId)
				.userNickname((String) attributes.get("name"))
				.userEmail((String) attributes.get("email"))
				.provider("google")
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
	}
	
	public UserDTO Entity() {
		return UserDTO.builder()
				.userId(userId)
				.userNickname(userNickname)
				.userEmail(userEmail)
				.provider(provider)
				.role(Role.GUEST)
				.build();
	}
}
