package com.project.toy.file.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.project.toy.file.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FileController {
	
	private final FileService fileService;
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@PostMapping("/upload")
	public String upload(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest upload) throws Exception {
		log.info("##### File Upload Page __ API #####");
		
		return fileService.upload(request, response, upload);
	}
}
