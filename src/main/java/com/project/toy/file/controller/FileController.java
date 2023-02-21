package com.project.toy.file.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.project.toy.file.service.FileService;

@RestController
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@PostMapping("/upload")
	public String upload(HttpServletRequest request, HttpServletResponse response, MultipartHttpServletRequest upload) throws Exception {
		return fileService.upload(request, response, upload);
	}
}
