package com.project.toy.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.toy.file.service.FileService;

@RestController
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@PostMapping("/upload")
	public String fileUploadFromCKEditor(@RequestPart MultipartFile upload) throws Exception {
		return fileService.upload(upload);
	}
}
