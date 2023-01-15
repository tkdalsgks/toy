package com.project.toy.security.oauth.provider;

public interface OAuth2UserInfo {

	String getUserId();
	String getUserEmail();
	String getUserNickname();
	String getProvider();
}
