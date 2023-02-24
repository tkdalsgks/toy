package com.project.toy.security.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.project.toy.security.handler.CustomAuthenticationFailureHandler;
import com.project.toy.security.handler.CustomAuthenticationSuccessHandler;
import com.project.toy.security.service.CustomOAuth2UserService;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;
	
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	
	@Autowired
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.headers().frameOptions().sameOrigin()
			.and()
				.csrf().disable()
				.authorizeRequests()
					.antMatchers("/swagger-ui/**", "/", "/join", "/check/**", "/find/**", "/certified/**").permitAll()
					.antMatchers("/board/**", "/comments/**", "/upload/**").hasAnyAuthority("ROLE_GUEST", "ROLE_USER", "ROLE_ADMIN")
					.antMatchers("/chat/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
					.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
					.anyRequest().authenticated()
			.and()
				.logout()
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID")
			.and()
				.formLogin()
					.loginPage("/")
					.loginProcessingUrl("/login")
					.defaultSuccessUrl("/")
					.failureUrl("/")
					.usernameParameter("userId")
					.passwordParameter("userPwd")
					.successHandler(customAuthenticationSuccessHandler)
					.failureHandler(customAuthenticationFailureHandler)
					.permitAll()
			.and()
				.oauth2Login()
					.loginPage("/")
					.defaultSuccessUrl("/")
					.permitAll()
					.userInfoEndpoint()
					.userService(customOAuth2UserService);
		
		return http.build();
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/error");
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/**
	 *  Spring Security 5.7.0 Update
	 *  https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
	 */
}
