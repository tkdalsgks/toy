package com.project.toy.common.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

	private String PATH = "/common/error/";
	
	@GetMapping("/error")
	public String handelError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		
		if(status != null) {
			int statusCode = Integer.valueOf(status.toString());
			
			if(statusCode == HttpStatus.BAD_REQUEST.value()) {
				return PATH + "400";
			}
			if(statusCode == HttpStatus.UNAUTHORIZED.value()) {
				return PATH + "401";
			}
			if(statusCode == HttpStatus.FORBIDDEN.value()) {
				return PATH + "403";
			}
			if(statusCode == HttpStatus.NOT_FOUND.value()) {
				return PATH + "404";
			}
			if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return PATH + "500";
			}
			if(statusCode == HttpStatus.BAD_GATEWAY.value()) {
				return PATH + "502";
			}
			if(statusCode == HttpStatus.SERVICE_UNAVAILABLE.value()) {
				return PATH + "503";
			}
		}
		
		return "error";
	}
}
