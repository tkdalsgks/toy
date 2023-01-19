package com.project.toy.security.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.project.toy.security.service.CustomOAuth2UserService;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.headers().frameOptions().sameOrigin()
			.and()
				.csrf().disable()					
				.httpBasic().disable()
				.authorizeRequests()
					.antMatchers("/", "/login", "/join").permitAll()
					.antMatchers("/chat/**").hasAuthority("ROLE_USER")
					.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
			.and()
				.logout()
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true)
			.and()
				.formLogin()
					.loginPage("/").permitAll()
					.loginProcessingUrl("/login")
					.defaultSuccessUrl("/")
					.failureUrl("/")
					.usernameParameter("userId")
					.passwordParameter("userPwd")
			.and().httpBasic()
			.and()
				.oauth2Login()
					.loginPage("/").permitAll()
					.defaultSuccessUrl("/")
					.userInfoEndpoint()
					.userService(customOAuth2UserService);
		
		return http.build();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
