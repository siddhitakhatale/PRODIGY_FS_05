package com.internship.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class FileUploadService {
	
	@Autowired
	private Cloudinary cloudinary;
	
	public String uploadFile(MultipartFile multipartFile) {
		try {
			Map uploadResult=cloudinary.uploader()
					.upload(multipartFile.getBytes(),
					ObjectUtils.asMap("resource_type","auto"));
			return uploadResult.get("secure_url").toString();
		}
		catch(IOException e) {
			throw new RuntimeException("file upload failed");
		}
		
	}
}
