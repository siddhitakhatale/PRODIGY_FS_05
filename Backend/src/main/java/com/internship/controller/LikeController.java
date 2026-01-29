package com.internship.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import com.internship.service.LikeService;

@RestController
@CrossOrigin
public class LikeController {
	
	@Autowired
	private LikeService likeService;
	
	@PostMapping("/posts/{postid}/like")
	public int likePost(@PathVariable Long postid) {
		return likeService.likePost(postid);
	}
}
