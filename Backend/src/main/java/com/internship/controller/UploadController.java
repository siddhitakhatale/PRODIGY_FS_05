package com.internship.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.internship.service.FileUploadService;

@RestController
@RequestMapping("/upload")
public class UploadController {
	@Autowired
	private FileUploadService fileUploadService;
	
	@PostMapping
	public Map<String,String> upload(@RequestParam MultipartFile file){
		String url=fileUploadService.uploadFile(file);
		return Map.of("url",url);
	}
}
