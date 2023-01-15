package com.project.toy.security.oauth.provider;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {

	private Map<String, Object> attributes;
	private Map<String, Object> attributesAccount;
	private Map<String, Object> attributesProfile;
	
	@SuppressWarnings("unchecked")
	public KakaoUserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
		this.attributesAccount = (Map<String, Object>)attributes.get("kakao_account");
		this.attributesProfile = (Map<String, Object>)attributes.get("properties");
	}

	@Override
	public String getProvider() {
		return "kakao";
	}

	@Override
	public String getUserId() {
		return String.valueOf(attributes.get("id"));
	}

	@Override
	public String getUserEmail() {
		return (String) attributesAccount.get("email");
	}

	@Override
	public String getUserNickname() {
		return (String) attributesProfile.get("nickname");
	}
	
}
